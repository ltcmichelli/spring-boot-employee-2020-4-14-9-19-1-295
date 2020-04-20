package com.thoughtworks.springbootemployee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyId;

    private String companyName;

    private Integer employeesNumber;

    @OneToMany(targetEntity = Employee.class, mappedBy = "companyId", fetch = FetchType.EAGER)
    private List<Employee> employees;

    public void update(Company updatedCompany) {
        if(updatedCompany.getCompanyName() != null){
            this.companyName = updatedCompany.companyName;
        }
        if(updatedCompany.getEmployeesNumber() != null){
            this.employeesNumber = updatedCompany.employeesNumber;
        }
        if(updatedCompany.getEmployees() != null){
            this.employees = updatedCompany.employees;
        }
    }
}
