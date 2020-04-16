package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
//    public List<Employee> employeeList = new ArrayList<>();

    @Autowired
    private EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

//    public EmployeeController() {
//        employeeList.add(new Employee(1, "Xiaoming", 20, "Male", 9000));
//        employeeList.add(new Employee(2, "Xiaohong", 19, "Female", 9000));
//        employeeList.add(new Employee(3, "Xiaozhi", 15, "Male", 9000));
//        employeeList.add(new Employee(4, "Xiaogang", 16, "Male", 9000));
//        employeeList.add(new Employee(5, "Xiaoxia", 15, "Female", 9000));
//    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Employee>> getEmployeeList(@RequestParam(value="gender", required=false) String gender,
                                                          @RequestParam(value="page", required=false) Integer page,
                                                          @RequestParam(value="pageSize", required=false) Integer pageSize) {
        List<Employee> resultEmployeeList = null;
        if (gender == null && page == null && pageSize == null){
            resultEmployeeList = service.getAllEmployeeList();
            return new ResponseEntity<>(resultEmployeeList, HttpStatus.OK);
        }

        if(gender != null){
            resultEmployeeList = service.getEmployeeByGender(gender);
            if (resultEmployeeList.isEmpty()){
                new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(resultEmployeeList, HttpStatus.OK);
        }

        resultEmployeeList = service.getEmployeeWithPaging(page, pageSize);

        if (resultEmployeeList.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(resultEmployeeList, HttpStatus.OK);
    }

    @GetMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int employeeId) {
        Employee resultEmployee = service.getEmployeeById(employeeId);

        if (resultEmployee == null){
            new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultEmployee, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<Employee>> addEmployee(@RequestBody Employee newEmployee) {
        Employee addedEmployee = service.addEmployee(newEmployee);
        if (addedEmployee == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        List<Employee> resultList = service.getAllEmployeeList();
        return new ResponseEntity<>(resultList, HttpStatus.CREATED);
    }

//    @DeleteMapping("/{employeeId}")
//    public ResponseEntity<Employee> deleteEmployee(@PathVariable int employeeId) {
//        Employee deleteEmployee = employeeList.stream().filter(employee -> employee.getEmployeeId() == employeeId).findFirst().orElse(null);
//
//        if (deleteEmployee == null) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//        employeeList.remove(deleteEmployee);
//
//        return new ResponseEntity<>(deleteEmployee, HttpStatus.OK);
//    }
//
    @PutMapping("/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int employeeId, @RequestBody Employee updateEmployee) {
        Employee updatedEmployee = service.updateEmployee(updateEmployee);

        if (updatedEmployee == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
    }
}
