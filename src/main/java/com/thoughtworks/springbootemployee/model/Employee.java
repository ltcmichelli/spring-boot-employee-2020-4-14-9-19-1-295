package com.thoughtworks.springbootemployee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;

    private Integer companyId;

    public void update(Employee updatedEmployee){
        if (updatedEmployee.getName() != null) {
            this.setName(updatedEmployee.getName());
        }

        if (updatedEmployee.getAge() != null) {
            this.setAge(updatedEmployee.getAge());
        }

        if (updatedEmployee.getGender() != null) {
            this.setGender(updatedEmployee.getGender());
        }

        if (updatedEmployee.getSalary() != null) {
            this.setSalary(updatedEmployee.getSalary());
        }
    }
}
