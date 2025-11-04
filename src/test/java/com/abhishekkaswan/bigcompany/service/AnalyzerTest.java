package com.abhishekkaswan.bigcompany.service;

import com.abhishekkaswan.bigcompany.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AnalyzerTest {
    private Map<String, Employee> employees;
    private Analyzer analyzer;

    @BeforeEach
    void setup(){
        employees = new HashMap<>();

        Employee ceo = new Employee("1","Alice","CEO",100000,null);
        Employee bob = new Employee("2","Bob","Dev",50000,"1");
        Employee marlon = new Employee("3","Marlon","Brando",48000,"1");
        Employee chap = new Employee("4","Chap","Delon",30000,"2");

        ceo.addSubordinate(bob);
        ceo.addSubordinate(marlon);
        bob.addSubordinate(chap);

        employees.put(ceo.getId(),ceo);
        employees.put(bob.getId(),bob);
        employees.put(marlon.getId(),marlon);
        employees.put(chap.getId(),chap);

        analyzer = new Analyzer();
    }

    @Test
    void testAnalyzeAllowedSalaries(){
        Map<String,Double> deviations = analyzer.analyzeAllowedSalaries(employees);

        assertTrue(deviations.containsKey("1"),"Alice should have salary deviation calculated.");
        assertFalse(deviations.containsKey("4"),"Non-managers should not be analyzed.");
    }

    @Test
    void testGetReportingLine(){
        Map<String,Integer> lines = analyzer.getReportingLine(employees);

        assertEquals(0,lines.get("1"),"Alice line should be 0");
        assertEquals(1,lines.get("2"),"Bob reports to Alice");
        assertEquals(2,lines.get("4"),"Chap reports to Bob -> Alice");
    }
}
