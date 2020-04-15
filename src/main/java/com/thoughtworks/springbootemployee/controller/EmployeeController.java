package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    public List<Employee> employeeList = new ArrayList<>();

    public EmployeeController() {
        employeeList.add(new Employee(0, "Xiaoming", 20, "Male"));
        employeeList.add(new Employee(1, "Xiaohong", 19, "Female"));
        employeeList.add(new Employee(2, "Xiaozhi", 15, "Male"));
        employeeList.add(new Employee(3, "Xiaogang", 16, "Male"));
        employeeList.add(new Employee(4, "Xiaoxia", 15, "Female"));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Employee>> getEmployeeList() {
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
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
