package com.abhishekkaswan.bigcompany.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EmployeeTest {
    @Test
    void testFullName(){
        Employee e = new Employee("101","John","Doe",50000,null);
        assertEquals("John Doe",e.getFullName(),"Full name should have first and last name");
    }

    @Test
    void testManagerIdTrimmingAndNull(){
        Employee e1 = new Employee("102","Brad","Smith",70000," 125  ");
        Employee e2 = new Employee("103","Sunita","Williams",81000,"");
        assertEquals("125",e1.getManagerId(),"Manager ID should be trimmed.");
        assertNull(e2.getManagerId(),"Blank manager ID should become null.");
    }

    @Test
    void testAddSubordinate(){
        Employee manager = new Employee("200","Manager1","Boss",91000,null);
        Employee emp = new Employee("203","Emp","Worker",61000,"200");

        manager.addSubordinate(emp);

        assertEquals(1,manager.getSubordinates().size());
        assertEquals(emp,manager.getSubordinates().get(0));
    }
}
