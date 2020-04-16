package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.Page;
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

    public Employee getEmployeeById(Integer employeeId) throws Exception {
        return repository.findById(employeeId);
    }

    public List<Employee> getEmployeeByGender(String gender) {
        return repository.findByGender(gender);
    }

    public List<Employee> getEmployeeWithPaging(Integer page, Integer pageSize) {
        List<Employee> employeeList = getAllEmployeeList();
        Page paging = new Page(page, pageSize);
        return paging.getPagingEmployeeList(employeeList);
    }

    public Employee addEmployee(Employee newEmployee) throws Exception {
        if (newEmployee == null) {
            throw new Exception();
        }
        if (getEmployeeById(newEmployee.getEmployeeId()) != null){
            throw new Exception();
        }
        return repository.save(newEmployee);
    }

    public Employee updateEmployee(Integer employeeId, Employee updatedEmployee) throws Exception {
        Employee targetEmployee = getEmployeeById(employeeId);
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

    public void deleteEmployee(Integer employeeId) throws Exception {
        repository.deleteById(employeeId);
    }

}
