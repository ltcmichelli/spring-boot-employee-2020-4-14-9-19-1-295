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

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> getAllEmployeeList() {
        return repository.findAll();
    }

    public List<Employee> getSpecificEmployeeList(String gender, Integer page, Integer pageSize) throws Exception {

        if (gender == null && page == null && pageSize == null) {
            return getAllEmployeeList();
        }

        if (gender != null) {
            return getEmployeeListByGender(gender);
        }

        return getEmployeeListWithPaging(page, pageSize);
    }


    public Employee getEmployeeById(Integer employeeId) throws Exception {
        return repository.findById(employeeId).orElseThrow(Exception::new);
    }

    public List<Employee> getEmployeeListByGender(String gender) throws Exception {
        List<Employee> resultList = repository.findAllByGender(gender);
        if (resultList.isEmpty()) {
            throw new Exception();
        }
        return resultList;
    }

    public List<Employee> getEmployeeListWithPaging(Integer page, Integer pageSize) throws Exception {
        List<Employee> employeeList = getAllEmployeeList();
        Page paging = new Page(page, pageSize);
        List<Employee> resultList = paging.getPagingEmployeeList(employeeList);
        if (resultList == null) {
            throw new Exception();
        }
        return resultList;
    }

    public Employee addEmployee(Employee newEmployee) throws Exception {
        if (newEmployee == null) {
            throw new Exception();
        }
        return repository.save(newEmployee);
    }
// TODO check the return
    public Employee updateEmployee(Integer employeeId, Employee updatedEmployee) throws Exception {
        if (getEmployeeById(employeeId) == null) {
            return null;
        }
        return repository.save(updatedEmployee);
    }

    public void deleteEmployee(Integer employeeId) throws Exception {
        repository.deleteById(employeeId);
    }

}
