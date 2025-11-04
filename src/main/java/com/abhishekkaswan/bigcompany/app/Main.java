package com.abhishekkaswan.bigcompany.app;

import com.abhishekkaswan.bigcompany.io.CSVReader;
import com.abhishekkaswan.bigcompany.model.Employee;
import com.abhishekkaswan.bigcompany.service.Analyzer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {

        Path csvPath;
        if(args.length>0 && Files.exists(Path.of(args[0]))){
            csvPath = Path.of(args[0]);
            System.out.println("Using CSV file from provided path : "+csvPath);
        }
        else{
            System.out.println("No valid CSV path provided. Using default 'employees_bigDataset.csv' from resources.\n");

            InputStream resourceStream = Main.class.getClassLoader().getResourceAsStream("employees_bigDataset.csv");
            if(resourceStream == null){
                System.out.println("OOPS ! Default resource 'employees_bigDataset.csv' not found JAR!");
                return;
            }
            Path tempFile = Files.createTempFile("employees",".csv");
            Files.copy(resourceStream,tempFile, StandardCopyOption.REPLACE_EXISTING);
            csvPath = tempFile;
        }

        Map<String, Employee> employees = CSVReader.readEmployees(csvPath);

        Analyzer analyzer = new Analyzer();
        Map<String,Double> deviations = analyzer.analyzeAllowedSalaries(employees);
        Map<String,Integer> lines = analyzer.getReportingLine(employees);

        System.out.println("**************************************************************");
        System.out.println("                     HR compliance checker                    ");
        System.out.println("**************************************************************");

        System.out.println("\n-------------Task 1 : Reporting Manager Salary Range----------");
        if(deviations.isEmpty()){
            System.out.println("All managers have salaries within the Allowed (20%-50%) range.");
        } else{
            System.out.println("Managers that are outside allowed salary range :  ");
            for(var entry : deviations.entrySet()){
                Employee e = employees.get(entry.getKey());
                double difference = entry.getValue();
                String status = difference>0?"Above":"Below";
                System.out.printf("%s (%s) : %.2f : %s allowed range%n",e.getFullName(),e.getId(),Math.abs(difference),status);
            }
        }

        System.out.println("\n----------------Task 2 : Reporting Line length-----------------");
        var lineLen = lines.entrySet().stream()
                .filter(e->e.getValue()>4)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if(lineLen.isEmpty()){
            System.out.println("No employee has more than 4 managers between them and the CEO.");
        } else {
            System.out.println("Employees with reporting depth more than 4 are : ");
            for(var entry : lineLen.entrySet()){
                Employee e = employees.get(entry.getKey());
                System.out.printf(" - %s (%s) depth = %d%n",e.getFullName(),e.getId(),entry.getValue());
            }
        }
    }
}
