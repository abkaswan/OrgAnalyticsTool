package com.abhishekkaswan.bigcompany.io;

import com.abhishekkaswan.bigcompany.model.Employee;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CSVReaderTest {
    @Test
    void testReadEmployeesAndLinks() throws IOException {
        String csvContent = """
                Id,firstName,lastName,salary,managerId
                1,Alice,CEO,100000,
                2,Bob,Dev,50000,1
                3,Charlie,Menon,45000,1
                """;
        Path tempFile = Files.createTempFile("test_employees",".csv");
        Files.writeString(tempFile,csvContent);

        Map<String, Employee> employees = CSVReader.readEmployees(tempFile);
        assertEquals(3,employees.size());
        assertTrue(employees.containsKey("1"));
        assertTrue(employees.containsKey("2"));

        Employee ceo = employees.get("1");
        assertEquals(2,ceo.getSubordinates().size(),"CEO should have 2 subordinates.");

        Employee bob = employees.get("2");
        assertEquals("1",bob.getManagerId());
    }
}
