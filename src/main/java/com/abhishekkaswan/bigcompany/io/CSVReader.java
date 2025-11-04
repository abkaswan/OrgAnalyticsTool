package com.abhishekkaswan.bigcompany.io;

import com.abhishekkaswan.bigcompany.model.Employee;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVReader {
    public static Map<String, Employee> readEmployees(Path csvPath) throws IOException {
        List<String> lines = Files.readAllLines(csvPath);
        Map<String,Employee> employees = new HashMap<>();

        for(String line : lines){
            line = line.trim();
            if(line.isEmpty()) continue;
            if(line.startsWith("Id")) continue;

            String[] parts = line.split(",",-1);
            if(parts.length<4) continue;

            String id = parts[0].trim();
            String firstName = parts[1].trim();
            String lastName = parts[1].trim();
            double salary = Double.parseDouble(parts[3].trim());
            String managerId = parts.length>4?parts[4].trim():null;

            Employee e = new Employee(id,firstName,lastName,salary,managerId);
            employees.put(id,e);
        }

        for(Employee e : employees.values()){
            String managerId = e.getManagerId();
            if(managerId!=null && employees.containsKey(managerId)){
                Employee manager = employees.get(managerId);
                manager.addSubordinate(e);
            }
        }

        return employees;

    }
}
