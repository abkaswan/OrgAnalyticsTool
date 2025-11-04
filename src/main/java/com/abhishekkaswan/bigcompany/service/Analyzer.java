package com.abhishekkaswan.bigcompany.service;

import com.abhishekkaswan.bigcompany.model.Employee;

import java.util.*;

public class Analyzer {
    public static final double minVal = 0.20;
    public static final double maxVal = 0.50;

    public Map<String,Double> analyzeAllowedSalaries(Map<String, Employee> employees){
        Map<String,Double> deviations = new HashMap<>();
        for(Employee e : employees.values()){
            List<Employee> subs = e.getSubordinates();
            if(subs.isEmpty()) continue;

            double avg = subs.stream().mapToDouble(Employee::getSalary).average().orElse(0.0);
            double minAllowed = avg*(1+minVal);
            double maxAllowed = avg*(1+maxVal);

            double salary = e.getSalary();
            if(salary<minAllowed){
                deviations.put(e.getId(),salary-minAllowed);
            }
            else if(salary>maxAllowed){
                deviations.put(e.getId(),salary-maxAllowed);
            }
        }
        return deviations;
    }

    public Map<String,Integer> getReportingLine(Map<String,Employee> employees){
        Map<String,Integer> depthList = new HashMap<>();

        Set<String> ceoIds = new HashSet<>();
        for(Employee e : employees.values()){
            if(e.getManagerId()==null) ceoIds.add(e.getId());
        }
        Queue<EmployeeLine> q = new LinkedList<>();
        for(String ceoId : ceoIds){
            Employee ceo = employees.get(ceoId);
            if(ceo!=null) q.add(new EmployeeLine(ceo,0));
        }

        while(!q.isEmpty()){
            EmployeeLine el = q.poll();

            depthList.put(el.employee.getId(),el.line);

            for(Employee sub : el.employee.getSubordinates()){
                q.add(new EmployeeLine(sub,el.line+1));
            }
        }
        return depthList;
    }

    private static class EmployeeLine{
        Employee employee;
        int line;

        EmployeeLine(Employee employee,int line){
            this.employee = employee;
            this.line = line;
        }

    }
}
