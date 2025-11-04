# OrgAnalyticsTool 
## A code assignment from Swiss Re.
A simple Java+Maven project that analyzes an organization's hierarchy from a csv file.
It checks : 
- Which managers earn outside the allowed salary Range (20%-50%) to their direct subordinates.
- Which employees have reporting lines **more than 4 levels**.

## Assumptions

### For CSV File
1. There are no quotes or commas inside names , we are splitting csv file by commas.
2. **Incomplete lines**(missing columns) are skipped automatically.

### Salary calculation rules
1. Employees **without subordinates** are skipped (they're not managers).
   - Reason : If we didn't skip them, the average of "zero" subordinates would be misleading.
2. The allowed salary range for manager is : 
   - Minimum : `average(subordinate salaries * 1.2`
   - Maximum : `average(subordinate salaries * 1.5`

## Built with:
-  Java 17
- Maven
- Junit 5 for testing

### How to Build, Test, and Run :
You can either **run inside Intellij** or **use Maven commands in the terminal**.

---

### Option 1 - Running inside Intellij IDEA
1. **Open** the project in Intellij and let it automatically import maven dependencies.
2. Right click on `Main.java` -> **Run 'Main.main()'**.
3. To provide a CSV file path:
    - Go to **Run -> Edit configurations -> Program Arguments**
    - Add something like : 
   ```
    /Path/To/CSV
    ```
4. If no arg is provided, the program automatically uses `resources/employee.csv`.

---

### Option 2 - Running from Terminal

### 1. Clean previous builds
```
mvn clean
```

### 2. Compile the project
```
mvn compile
```

### 3. Run unit tests (JUnit 5)
```
mvn test
```
If tests pass , you'll see : 
`[INFO] BUILD SUCCESS`

To run a specific test class : 
```
mvn -Dtest=AnalyzerTest test
```

### 4. Build the executable Jar
```
mvn package
```
This creates the JAR at : 
`target/OrgAnalyticsTool-1.0-SNAPSHOT.jar`

### 5. Run the JAR 
A. Using an external CSV file
```
java -jar target/OrgAnalyticsTool-1.0-SNAPSHOT.jar /path/to/employees.csv
```

B. Using the default CSV file (from resources)
```
java -jar target/OrgAnalyticsTool-1.0-SNAPSHOT.jar
```

---

## Author
Abhishek Kaswan

contact : `abkaswan2000@gmail.com`
