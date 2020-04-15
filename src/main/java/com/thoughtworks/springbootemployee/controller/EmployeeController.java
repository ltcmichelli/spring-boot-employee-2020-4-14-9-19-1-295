package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
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
    public List<Employee> getEmployeeList(){
        return employeeList;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Employee> addEmployee(@RequestBody Employee employee){
        employeeList.add(employee);
        return employeeList;
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable int employeeId){
        Employee deleteEmployee = employeeList.stream().filter(employee -> employee.getEmployeeId() == employeeId).findFirst().orElse(null);

        if (deleteEmployee == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        employeeList.remove(deleteEmployee);

        return new ResponseEntity<>(deleteEmployee, HttpStatus.OK);
    }
}
