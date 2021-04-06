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
- Employee & Department data from csv file

### Create new Employee and associate with department
- MethodName : com.employeemanagement.services.EmployeeService.saveEmployee

### Create new Department
- MethodName : com.employeemanagement.services.DepartmentService.saveDepartment

### Find all employees by department & above given age
- MethodName : com.employeemanagement.services.OperationalService.findAllByDepartmentAndAge

### Find all employees below a given age having CTC greater than 2Lakhs
- MethodName : com.employeemanagement.services.OperationalService.findAllByDepartmentAndAgeAndCtcAndStatus

### Find all employees across all departments having status as ACTIVE and living in Mumbai city with a CTC range of 2 to 5
- MethodName : com.employeemanagement.services.OperationalService.findAllByDepartmentsAndCityAndCtc
