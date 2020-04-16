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

    @Autowired
    private EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Employee>> getEmployeeList(@RequestParam(value = "gender", required = false) String gender,
                                                          @RequestParam(value = "page", required = false) Integer page,
                                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        try{
            List<Employee> resultEmployeeList = service.getSpecificEmployeeList(gender, page, pageSize);
            return new ResponseEntity<>(resultEmployeeList, HttpStatus.OK);
        }catch(Exception exception){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer employeeId) {
        try{
            Employee resultEmployee = service.getEmployeeById(employeeId);
            return new ResponseEntity<>(resultEmployee, HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee newEmployee) {
        try{
            Employee employee = service.addEmployee(newEmployee);
            return new ResponseEntity<>(employee, HttpStatus.CREATED);

        }catch (Exception exception){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Integer employeeId) {
        try{
            service.deleteEmployee(employeeId);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }catch (Exception exception){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Integer employeeId, @RequestBody Employee updateEmployee) {
        try{
            Employee employee = service.updateEmployee(employeeId, updateEmployee);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
