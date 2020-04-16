package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    public List<Employee> employeeList = new ArrayList<>();

    public EmployeeRepository(){
        employeeList.add(new Employee(1, "Xiaoming", 20, "Male", 9000));
        employeeList.add(new Employee(2, "Xiaohong", 19, "Female", 9000));
        employeeList.add(new Employee(3, "Xiaozhi", 15, "Male", 9000));
        employeeList.add(new Employee(4, "Xiaogang", 16, "Male", 9000));
        employeeList.add(new Employee(5, "Xiaoxia", 15, "Female", 9000));

    }

    public List<Employee> findAll() {
        return employeeList;
    }
}
