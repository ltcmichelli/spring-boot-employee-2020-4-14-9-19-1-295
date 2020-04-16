package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Company>> getCompanyList(@RequestParam(value = "page", required = false) Integer page,
                                                        @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        try {
            List<Company> resultCompanyList = service.getSpecificCompanyList(page, pageSize);
            return new ResponseEntity<>(resultCompanyList, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Company> getCompanyById(@PathVariable Integer companyId) {
        try {
            Company resultCompany = service.getCompanyById(companyId);
            return new ResponseEntity<>(resultCompany, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{companyId}/employees")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Employee>> getEmployeeListByCompanyId(@PathVariable int companyId) {
        try {
            List<Employee> resultEmployeeList = service.getEmployeeListByCompanyId(companyId);
            return new ResponseEntity<>(resultEmployeeList, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Company> addCompany(@RequestBody Company newCompany) {
        try {
            service.addCompany(newCompany);
            return new ResponseEntity<>(null, HttpStatus.CREATED);

        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<Company> updateCompany(@PathVariable Integer companyId, @RequestBody Company updateCompany) {
        try {
            Company company = service.updateCompany(companyId, updateCompany);
            return new ResponseEntity<>(company, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{companyId}")
    public ResponseEntity<Company> deleteCompany(@PathVariable int companyId) {
        try {
            service.deleteCompany(companyId);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
