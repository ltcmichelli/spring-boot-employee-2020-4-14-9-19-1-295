package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class CompanyRepository {
    public List<Company> companyList = new ArrayList<>();

    public CompanyRepository(){
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

    public List<Company> findAll() {
        return companyList;
    }

    public Company findById(Integer companyId) throws Exception {
        return companyList.stream().filter(company -> company.getCompanyId().equals(companyId)).findFirst().orElseThrow(Exception::new);
    }

    public Company save(Company company) {
        companyList.add(company);
        return company;
    }

    public void deleteById(Integer companyId) throws Exception {
        Company targetCompany = findById(companyId);
        companyList.remove(targetCompany);
    }

}
