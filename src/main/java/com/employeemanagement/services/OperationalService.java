package com.employeemanagement.services;

import com.employeemanagement.entities.Department;
import com.employeemanagement.entities.Employee;
import com.employeemanagement.pojo.DepartmentPojo;
import com.employeemanagement.pojo.EmployeePojo;
import com.employeemanagement.utils.StatusEnum;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.employeemanagement.utils.FileEnum;
/**
 * The type Data initializing com.employeemanagement.service.
 */
@Service
public class OperationalService {
    private static final Logger logger = LoggerFactory.getLogger(OperationalService.class);

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DepartmentService departmentService;

    /**
     * Reads data from csv file and returns in Map object according to header provided.
     *
     * @param headers  File headers array.
     * @param fileName File name with path.
     * @return the list csv data in map
     */
    public List<Map<String, String>> loadData(String[] headers, String fileName) {
        logger.info("Data Loading Started");
        List<Map<String, String>> csvDataList = new ArrayList<>();
        try {
            Files.lines(Paths.get(fileName))
                    .skip(1) // ignore headers
                    .forEach(line -> {
                        Map<String, String> map = new HashMap<>();
                        String[] csvData = line.split(",");
                        for (int j = 0; j < csvData.length; j++) map.put(headers[j], csvData[j]);
                        csvDataList.add(map);
                    });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Failed loadData : " + e.getMessage());
        }
        logger.info("Data Loading Ended");
        return csvDataList;
    }

    /**
     * Write data from to given filename.
     *
     * @param headers     the headers
     * @param fileName    the file name
     * @param csvDataList the csv data list
     */
    public void writeData(String[] headers, String fileName, List<Map<String, String>> csvDataList) {
        try {
            File csvOutputFile = new File(fileName);
            if (!csvOutputFile.exists()) if (!csvOutputFile.createNewFile()) {
                logger.info("Unable to create new file");
                return;
            }
            FileWriter csvWriter = new FileWriter(csvOutputFile, false);
            CSVWriter writer = new CSVWriter(csvWriter);
            //Set Headers
            writer.writeNext(headers);
            //Set Data to CSV file
            for (Map<String, String> csvData : csvDataList) {
                String[] data = new String[headers.length];
                for (int i = 0; i < headers.length; i++)
                    data[i] = csvData.get(headers[i]);

                writer.writeNext(data);
            }
            logger.info("Data response is written to File : " + fileName);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }


    /**
     * Loads Employee csv data and insert into database.
     */
    public void loadEmployeeData() {
        logger.info("Loading Employee Data Start");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String[] headers = Arrays.stream(FileEnum.EmployeeFileHeader.values()).map(FileEnum.EmployeeFileHeader::getValue).toArray(String[]::new);
        List<Map<String, String>> employeeCsvDataList = loadData(headers, "src/main/resources/EmployeeData.csv");

        employeeCsvDataList.parallelStream().forEach(employeeCsvData -> {
            try {
                String userName = employeeCsvData.get(FileEnum.EmployeeFileHeader.NAME.getValue());
                String password = employeeCsvData.get(FileEnum.EmployeeFileHeader.PASSWORD.getValue());

                String ageStr = employeeCsvData.get(FileEnum.EmployeeFileHeader.AGE.getValue());
                int age = 0;
                if (ageStr != null && !ageStr.isEmpty())
                    age = Integer.parseInt(employeeCsvData.get(FileEnum.EmployeeFileHeader.AGE.getValue()));
                String ctcStr = employeeCsvData.get(FileEnum.EmployeeFileHeader.ANNUAL_CTC.getValue());
                double ctc = 0.0;
                if (ctcStr != null && !ctcStr.isEmpty())
                    ctc = Double.parseDouble(employeeCsvData.get(FileEnum.EmployeeFileHeader.ANNUAL_CTC.getValue()));
                String departmentName = employeeCsvData.get(FileEnum.EmployeeFileHeader.DEPARTMENT_NAME.getValue());
                String city = employeeCsvData.get(FileEnum.EmployeeFileHeader.CITY.getValue());
                String state = employeeCsvData.get(FileEnum.EmployeeFileHeader.STATE.getValue());
                String pincode = employeeCsvData.get(FileEnum.EmployeeFileHeader.PINCODE.getValue());
                String country = employeeCsvData.get(FileEnum.EmployeeFileHeader.COUNTRY.getValue());
                String region = employeeCsvData.get(FileEnum.EmployeeFileHeader.REGION.getValue());
                String dateStr = employeeCsvData.get(FileEnum.EmployeeFileHeader.JOINING_DATE.getValue());

                LocalDate joiningDate = null;
                if (dateStr != null && !dateStr.isEmpty())
                    joiningDate = LocalDate.parse(employeeCsvData.get(FileEnum.EmployeeFileHeader.JOINING_DATE.getValue()), formatter);
                EmployeePojo employeePojo = new EmployeePojo();
                employeePojo.setUsername(userName);
                employeePojo.setPassword(password);
                employeePojo.setAge(age);
                employeePojo.setCtc(ctc);
                employeePojo.setDeptName(departmentName);
                employeePojo.setCity(city);
                employeePojo.setState(state);
                employeePojo.setCountry(country);
                employeePojo.setPinCode(pincode);
                employeePojo.setJoiningDate(joiningDate);
                employeePojo.setRegion(region);

                String response = employeeService.saveEmployee(employeePojo);
                employeeCsvData.put(FileEnum.EmployeeFileHeader.STATUS.getValue(), response);
            } catch (Exception ex) {
                ex.printStackTrace();
                employeeCsvData.put(FileEnum.EmployeeFileHeader.STATUS.getValue(), "Failed : " + ex.getMessage());
            }
        });

        //Write Data
        writeData(headers, "src/main/resources/EmployeeData_Response.csv", employeeCsvDataList);
        logger.info("Loading Employee Data End");
    }

    /**
     * Loads Department csv data and insert into database.
     */
    public void loadDepartmentData() {
        logger.info("Loading Department Data Start");
        String[] headers = Arrays.stream(FileEnum.DepartmentFileHeader.values()).map(FileEnum.DepartmentFileHeader::getValue).toArray(String[]::new);
        List<Map<String, String>> deptCsvDataList = loadData(headers, "src/main/resources/DepartmentData.csv");

        for (Map<String, String> deptCsvData : deptCsvDataList)
            try {
                String departmentName = deptCsvData.get(FileEnum.DepartmentFileHeader.DEPT_NAME.getValue());
                String region = deptCsvData.get(FileEnum.DepartmentFileHeader.REGION.getValue());
                DepartmentPojo departmentPojo = new DepartmentPojo();
                departmentPojo.setName(departmentName);
                departmentPojo.setRegion(region);
                String responseMessage = departmentService.saveDepartment(departmentPojo);
                deptCsvData.put(FileEnum.DepartmentFileHeader.STATUS.getValue(), responseMessage);
            } catch (Exception ex) {
                ex.printStackTrace();
                deptCsvData.put(FileEnum.DepartmentFileHeader.STATUS.getValue(), "Failed : " + ex.getMessage());
            }
        //Write Data
        writeData(headers, "src/main/resources/DepartmentData_Response.csv", deptCsvDataList);
        logger.info("Loading Department Data End");
    }

    /**
     * Find all by department and age above and status.
     *
     * @param department the department
     * @param age        the age
     * @param status     the status
     * @return the employee list
     */
    public List<EmployeePojo> findAllByDepartmentAndAge(String department, Integer age, StatusEnum status) {
        return employeeService.findAll().stream()
                .filter(employee ->
                        employee.getDepartment().getName().equals(department) &&
                                employee.getAge() > age &&
                                employee.getStatus().equals(status))
                .map(employee -> setEmployeePojoData(employee))
                .collect(Collectors.toList());
    }

    /**
     * Find all by department and age and ctc and status list.
     *
     * @param department the department
     * @param age        the age
     * @param ctc        the ctc
     * @param status     the status
     * @return the list
     */
    public List<EmployeePojo> findAllByDepartmentAndAgeAndCtcAndStatus(String department, Integer age, Double ctc, StatusEnum status) {
        return employeeService.findAll().stream()
                .filter(employee ->
                        employee.getDepartment().getName().equals(department) &&
                        employee.getAge() > age &&
                        employee.getCtc() > ctc &&
                        employee.getStatus().equals(status))
                .map(employee -> setEmployeePojoData(employee))
                .collect(Collectors.toList());
    }

    /**
     * Find all employees across all departments and city and ctc range.
     *
     * @param city             the city
     * @param ctcFrom          the ctc from
     * @param ctcTo            the ctc to
     * @return the list of employees
     */
    public List<EmployeePojo> findAllByDepartmentsAndCityAndCtc(String city, Double ctcFrom, Double ctcTo) {
        List<EmployeePojo> employeePojoList = new ArrayList<>();
        List<Future<List<EmployeePojo>>> resultList = new ArrayList<>();
        List<Map<String, String>> csvEmployeeData = new ArrayList<>();
        try {
            List<Department> departmentList = departmentService.findAll();
            ExecutorService executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(departmentList.size());

            for (Department department : departmentList) {
                Callable<List<EmployeePojo>> callable = new Callable() {
                    @Override
                    public List<EmployeePojo> call() {
                        return employeeService.findAll().stream()
                                .filter(employee ->
                                        employee.getDepartment().getName().equals(department.getName()) &&
                                                employee.getDepartment().getRegion().equals(department.getRegion()) &&
                                                employee.getAddress().getCity().equals(city) &&
                                                employee.getCtc() > ctcFrom && employee.getCtc() < ctcTo)
                                .map(employee -> setEmployeePojoData(employee)).collect(Collectors.toList());
                    }
                };
                resultList.add(executor.submit(callable));
            }

            for (Future<List<EmployeePojo>> result : resultList) {
                employeePojoList.addAll(result.get());
            }
            executor.shutdown();

            executor.awaitTermination(10, TimeUnit.SECONDS);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return employeePojoList;
    }

    /**
     * Write employee data to csv.
     *
     * @param employeePojoList the employee pojo list
     */
    public void writeEmployeeDataToCsv(List<EmployeePojo> employeePojoList){
        String[] headers = {FileEnum.EmployeeFileHeader.NAME.getValue(), FileEnum.EmployeeFileHeader.DEPARTMENT_NAME.getValue(), FileEnum.EmployeeFileHeader.REGION.getValue(), FileEnum.EmployeeFileHeader.CITY.getValue(), FileEnum.EmployeeFileHeader.ANNUAL_CTC.getValue()};
        List<Map<String,String>> csvEmployeeData = new ArrayList<>();
        employeePojoList.forEach(employeePojo -> {
            Map<String, String> map = new HashMap<>();
            map.put(FileEnum.EmployeeFileHeader.NAME.getValue(), employeePojo.getUsername());
            map.put(FileEnum.EmployeeFileHeader.DEPARTMENT_NAME.getValue(), employeePojo.getDeptName());
            map.put(FileEnum.EmployeeFileHeader.REGION.getValue(), employeePojo.getRegion());
            map.put(FileEnum.EmployeeFileHeader.CITY.getValue(), employeePojo.getCity());
            map.put(FileEnum.EmployeeFileHeader.ANNUAL_CTC.getValue(), employeePojo.getCtc().toString());
            csvEmployeeData.add(map);
        });
        writeData(headers, "src/main/resources/Employee_Filtered_Response.csv", csvEmployeeData);
    }

    private EmployeePojo setEmployeePojoData(Employee employee) {
        EmployeePojo employeePojo = new EmployeePojo();
        employeePojo.setUsername(employee.getUsername());
        employeePojo.setDeptName(employee.getDepartment().getName());
        employeePojo.setRegion(employee.getDepartment().getRegion().getValue());
        employeePojo.setAge(employee.getAge());
        employeePojo.setStatus(employee.getStatus().getValue());
        employeePojo.setCtc(employee.getCtc());
        employeePojo.setCity(employee.getAddress().getCity());
        return employeePojo;
    }
}
