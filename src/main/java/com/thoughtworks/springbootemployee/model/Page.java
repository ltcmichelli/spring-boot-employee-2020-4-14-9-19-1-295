package com.thoughtworks.springbootemployee.model;

import java.util.List;

public class Page {
    public Integer page;
    public Integer pageSize;

    public Page(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public List<Employee> getPagingEmployeeList(List<Employee> employeeList) {
        if (employeeList.isEmpty()){
            return null;
        }

        if (page <= 0 || pageSize <= 0){
            return null;
        }

        Integer lowerBound = (page - 1) * pageSize;
        Integer upperBound = page * pageSize;
        if (upperBound > employeeList.size()) {
            upperBound = employeeList.size();
        }
        return employeeList.subList(lowerBound, upperBound);
    }

    public List<Company> getPagingCompanyList(List<Company> companyList) {
        if (companyList.isEmpty()){
            return null;
        }

        if (page <= 0 || pageSize <= 0){
            return null;
        }

        Integer lowerBound = (page - 1) * pageSize;
        Integer upperBound = page * pageSize;
        if (upperBound > companyList.size()) {
            upperBound = companyList.size();
        }
        return companyList.subList(lowerBound, upperBound);
    }
}
