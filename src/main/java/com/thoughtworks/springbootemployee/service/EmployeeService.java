package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;

    public List<Employee> getAllEmployeeList() {
        return repository.findAll();
    }

    public Employee getEmployeeById(int employeeId) {
        return repository.findById(employeeId);
    }

    public Employee addEmployee(Employee newEmployee) {
        if (newEmployee == null) {
            return null;
        }
        return repository.save(newEmployee);
    }

    public Employee updateEmployee(Employee updatedEmployee) {
        Employee targetEmployee = getEmployeeById(updatedEmployee.getEmployeeId());
        if (targetEmployee == null) {
            return null;
        }

        if (updatedEmployee.getName() != null) {
            targetEmployee.setName(updatedEmployee.getName());
        }

        if (updatedEmployee.getAge() != null) {
            targetEmployee.setAge(updatedEmployee.getAge());
        }

        if (updatedEmployee.getGender() != null) {
            targetEmployee.setGender(updatedEmployee.getGender());
        }

        if (updatedEmployee.getSalary() != null) {
            targetEmployee.setSalary(updatedEmployee.getSalary());
        }
        repository.deleteById(targetEmployee.getEmployeeId());
        return repository.save(targetEmployee);

    }

}
