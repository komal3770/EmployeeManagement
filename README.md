# EmployeeManagement

## Project Pre-requisite 
- Spring Boot
- Spring Data JPA
- H2 Database
- JAVA 8
- MAVEN
- JUnit

## Main Class: com.employeemanagement.EmployeeManagementApplication

### Running From command line :
Go to /employee-management & enter below command
> mvn exec:java  -Dexec.mainClass=com.employeemanagement.EmployeeManagementApplication

### Running from IntelliJ :
> Right-click on Main class and Select "Run EmployeeManagementApplication"

### Running from Eclipse : 
> Right-click on Main class and Select "Run As Java Application"

### Running Test Cases
> mvn test

## H2 Database
- URL : http://localhost:8080/h2-ui

## FUNCTIONALITY ACHIEVED

### Populate the database with dummy data for 20 employees divided between 2 departments
- Employee & Department data from csv file(from resources folder) & Record status is updated in EmployeeData_Response.csv & DepartmentData_Response.csv
- Class : com.employeemanagement.services.OperationalService
- Methods : loadDepartmentData & loadEmployeeData

### Create new Employee and associate with department
- Class : com.employeemanagement.services.EmployeeService
- MethodName : saveEmployee

### Create new Department
- Class : com.employeemanagement.services.DepartmentService
- MethodName : saveDepartment

### Find all employees by department & above given age
- Class : com.employeemanagement.services.OperationalService
- MethodName : findAllByDepartmentAndAge

### Find all employees below a given age having CTC greater than 2Lakhs
- Class : com.employeemanagement.services.OperationalService
- MethodName : findAllByDepartmentAndAgeAndCtcAndStatus

### Find all employees across all departments having status as ACTIVE and living in Mumbai city with a CTC range of 2 to 5
- Class : com.employeemanagement.services.OperationalService
- MethodName : findAllByDepartmentsAndCityAndCtc

Test cases for above functionalities are present
