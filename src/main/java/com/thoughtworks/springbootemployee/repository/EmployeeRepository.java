package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public List<Employee> findAll() {
        return employeeList;
    }

    public Employee findById(Integer employeeId) throws Exception {
        return employeeList.stream().filter(employee -> employee.getEmployeeId().equals(employeeId)).findFirst().orElseThrow(Exception::new);
    }

    public Employee save(Employee employee) {
        employeeList.add(employee);
        return employee;
    }

    public void deleteById(Integer employeeId) throws Exception {
        Employee targetEmployee = findById(employeeId);
        employeeList.remove(targetEmployee);
    }

    public List<Employee> findByGender(String gender) {
        return employeeList.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
    }
}
