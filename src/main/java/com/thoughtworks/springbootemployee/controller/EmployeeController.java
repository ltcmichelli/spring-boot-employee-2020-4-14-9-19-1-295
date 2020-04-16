package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    public List<Employee> employeeList = new ArrayList<>();

    public EmployeeController() {
        employeeList.add(new Employee(1, "Xiaoming", 20, "Male", 9000));
        employeeList.add(new Employee(2, "Xiaohong", 19, "Female", 9000));
        employeeList.add(new Employee(3, "Xiaozhi", 15, "Male", 9000));
        employeeList.add(new Employee(4, "Xiaogang", 16, "Male", 9000));
        employeeList.add(new Employee(5, "Xiaoxia", 15, "Female", 9000));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Employee>> getEmployeeList(@RequestParam(value="gender", required=false) String gender,
                                                          @RequestParam(value="page", required=false) Integer page,
                                                          @RequestParam(value="pageSize", required=false) Integer pageSize) {
        List<Employee> resultEmployeeList;
        if (gender == null && page == null && pageSize == null){
            return new ResponseEntity<>(employeeList, HttpStatus.OK);
        }

        if(gender != null){
            resultEmployeeList = employeeList.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
            if (resultEmployeeList.isEmpty()){
                new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(resultEmployeeList, HttpStatus.OK);
        }

        Page paging = new Page(page, pageSize);
        resultEmployeeList = paging.getPagingEmployeeList(employeeList);

        if (resultEmployeeList.isEmpty()){
            new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(resultEmployeeList, HttpStatus.OK);
    }

    @GetMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int employeeId) {
        Employee resultEmployee = employeeList.stream().filter(employee -> employee.getEmployeeId() == employeeId).findFirst().orElse(null);

        if (resultEmployee == null){
            new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultEmployee, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee newEmployee) {
        if (newEmployee == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Employee employeeWithSameId = employeeList.stream().filter(employee -> employee.getEmployeeId() == newEmployee.getEmployeeId()).findFirst().orElse(null);
        if (employeeWithSameId != null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        employeeList.add(newEmployee);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable int employeeId) {
        Employee deleteEmployee = employeeList.stream().filter(employee -> employee.getEmployeeId() == employeeId).findFirst().orElse(null);

        if (deleteEmployee == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        employeeList.remove(deleteEmployee);

        return new ResponseEntity<>(deleteEmployee, HttpStatus.OK);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int employeeId, @RequestBody Employee updateEmployee) {
        Employee employeeInList = employeeList.stream().filter(employee -> employee.getEmployeeId() == employeeId).findFirst().orElse(null);

        if (employeeInList == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        employeeList.remove(employeeInList);
        employeeList.add(updateEmployee);

        return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
    }
}
