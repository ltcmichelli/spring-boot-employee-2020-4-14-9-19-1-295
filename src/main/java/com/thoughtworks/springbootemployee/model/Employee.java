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
    public Integer employeeId;
    public String name;
    public Integer age;
    public String gender;
    public Integer salary;

    public void update(Employee updatedEmployee){
        Employee targetEmployee = new Employee();
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
    }
}
