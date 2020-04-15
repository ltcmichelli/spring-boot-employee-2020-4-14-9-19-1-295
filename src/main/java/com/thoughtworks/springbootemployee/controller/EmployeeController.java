package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    public List<Employee> employeeList = new ArrayList<>(Arrays.asList(
            new Employee(0, "Xiaoming", 20, "Male"),
            new Employee(1, "Xiaohong", 19, "Female"),
            new Employee(2, "Xiaozhi", 15, "Male"),
            new Employee(3, "Xiaogang", 16, "Male"),
            new Employee(4, "Xiaoxia", 15, "Female")
    ));

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


}
