package com.thoughtworks.springbootemployee.model;

public class Employee {
    public int employeeId;
    public String name;
    public int age;
    public String gender;

    public Employee(int employeeId, String name, int age, String gender) {
        this.employeeId = employeeId;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public boolean isSameEmployeeId(int employeeId){
        if (this.employeeId == employeeId){
            return true;
        }
        return false;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
