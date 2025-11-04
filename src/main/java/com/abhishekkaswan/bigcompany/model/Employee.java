package com.abhishekkaswan.bigcompany.model;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final double salary;
    private final String managerId;
    private final List<Employee> subordinates = new ArrayList<>();


    public Employee(String id, String firstName, String lastName, double salary, String managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = (managerId==null || managerId.trim().isEmpty()?null:managerId.trim());
    }

    public String getId(){return id;}
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
    public double getSalary(){return salary;}
    public String getManagerId(){return managerId;}
    public List<Employee> getSubordinates(){return subordinates;}

    public void addSubordinate(Employee employee){
        subordinates.add(employee);
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

    @Override
    public String toString(){
        return id+": "+getFullName()+" ("+salary+")";
    }

}
