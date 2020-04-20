package com.thoughtworks.springbootemployee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;

    private String name;

    private Integer age;

    private String gender;

    private Integer salary;

    private Integer companyId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parking_boy_id", referencedColumnName = "id")
    private ParkingBoy parkingBoy;

    public void update(Employee updatedEmployee) {
        if(updatedEmployee.getName() != null){
            this.name = updatedEmployee.name;
        }
        if(updatedEmployee.getAge() != null){
            this.age = updatedEmployee.age;
        }
        if(updatedEmployee.getGender() != null){
            this.gender = updatedEmployee.gender;
        }
        if(updatedEmployee.getSalary() != null){
            this.salary = updatedEmployee.salary;
        }
        if(updatedEmployee.getCompanyId() != null){
            this.companyId = updatedEmployee.companyId;
        }
        if(updatedEmployee.getParkingBoy() != null){
            this.parkingBoy = updatedEmployee.parkingBoy;
        }
    }
}
