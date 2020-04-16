package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    public List<Company> companyList = new ArrayList<>();

    public CompanyController() {
        List<Employee> employeeListInCompanyA = Arrays.asList(
                new Employee(1, "Xiaoming", 20, "Male", 9000),
                new Employee(2, "Xiaohong", 19, "Female", 8000),
                new Employee(3, "Xiaozhi", 15, "Male", 7000),
                new Employee(4, "Xiaogang", 16, "Male", 7500),
                new Employee(5, "Xiaoxia", 15, "Female", 8500)
        );
        List<Employee> employeeListInCompanyB = Arrays.asList(
                new Employee(6, "Amy", 20, "Female", 9000),
                new Employee(7, "Ben", 19, "Male", 9000),
                new Employee(8, "Cathy", 15, "Female", 9000),
                new Employee(9, "David", 16, "Male", 9000)
        );
        Company companyA = new Company(1,"Company A", 200, employeeListInCompanyA);
        Company companyB = new Company(2,"Company B", 100, employeeListInCompanyB);

        companyList.add(companyA);
        companyList.add(companyB);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Company>> getCompanyList(@RequestParam(value="page", required=false) Integer page,
                                                        @RequestParam(value="pageSize", required=false) Integer pageSize) {
        List<Company> resultCompanyList;
        if (page == null && pageSize == null){
            return new ResponseEntity<>(companyList, HttpStatus.OK);
        }

        Page paging = new Page(page, pageSize);
        resultCompanyList = paging.getPagingCompanyList(companyList);

        if (resultCompanyList.isEmpty()){
            new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(resultCompanyList, HttpStatus.OK);
    }

    @GetMapping("/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Company> getCompanyById(@PathVariable int companyId) {
        Company resultCompany = companyList.stream().filter(company -> company.getCompanyId() == companyId).findFirst().orElse(null);

        if (resultCompany == null){
            new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultCompany, HttpStatus.OK);
    }

    @GetMapping("/{companyId}/employees")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Employee>> getEmployeeListByCompanyId(@PathVariable int companyId) {
        Company resultCompany = companyList.stream().filter(company -> company.getCompanyId() == companyId).findFirst().orElse(null);

        if (resultCompany == null){
            new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultCompany.getEmployees(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Company> addCompany(@RequestBody Company newCompany) {
        if (newCompany == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Company companyWithSameId = companyList.stream().filter(company -> company.getCompanyId() == newCompany.getCompanyId()).findFirst().orElse(null);
        if (companyWithSameId != null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        companyList.add(newCompany);
        return new ResponseEntity<>(newCompany, HttpStatus.CREATED);
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<Company> updateCompany(@PathVariable int companyId, @RequestBody Company updateCompany) {
        Company companyInList = companyList.stream().filter(company -> company.getCompanyId() == companyId).findFirst().orElse(null);

        if (companyInList == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        companyList.remove(companyInList);
        companyList.add(updateCompany);

        return new ResponseEntity<>(updateCompany, HttpStatus.OK);
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<Company> deleteCompany(@PathVariable int companyId) {
        Company deleteCompany = companyList.stream().filter(company -> company.getCompanyId() == companyId).findFirst().orElse(null);

        if (deleteCompany == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        companyList.remove(deleteCompany);

        return new ResponseEntity<>(deleteCompany, HttpStatus.OK);
    }
}
