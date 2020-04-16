package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Page;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository repository;

    public List<Company> getAllCompanyList() {
        return repository.findAll();
    }

    public List<Company> getSpecificEmployeeList(Integer page, Integer pageSize) throws Exception {

        if (page == null && pageSize == null) {
            return getAllCompanyList();
        }

        return getEmployeeWithPaging(page, pageSize);
    }


    public Company getCompanyById(Integer companyId) throws Exception {
        return repository.findById(companyId);
    }


    public List<Company> getEmployeeWithPaging(Integer page, Integer pageSize) throws Exception {
        List<Company> companyList = getAllCompanyList();
        Page paging = new Page(page, pageSize);
        List<Company> resultList = paging.getPagingCompanyList(companyList);
        if (resultList == null) {
            throw new Exception();
        }
        return resultList;
    }

    public Company addEmployee(Company company) throws Exception {
        if (company == null) {
            throw new Exception();
        }
        if (getCompanyById(company.getCompanyId()) != null) {
            throw new Exception();
        }
        return repository.save(company);
    }

    public Company updateCompany(Integer companyId, Company updatedCompany) throws Exception {
        Company targetCompany = getCompanyById(companyId);
        if (targetCompany == null) {
            return null;
        }

        if (updatedCompany.getCompanyName() != null) {
            targetCompany.setCompanyName(updatedCompany.getCompanyName());
        }

        if (updatedCompany.getEmployeesNumber() != null) {
            targetCompany.setEmployeesNumber(updatedCompany.getEmployeesNumber());
        }

        if (updatedCompany.getEmployees() != null) {
            targetCompany.setEmployees(updatedCompany.getEmployees());
        }

        repository.deleteById(targetCompany.getCompanyId());
        return repository.save(targetCompany);
    }

    public void deleteEmployee(Integer companyId) throws Exception {
        repository.deleteById(companyId);
    }

}