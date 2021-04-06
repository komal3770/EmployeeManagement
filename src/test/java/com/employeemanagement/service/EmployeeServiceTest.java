package com.employeemanagement.service;

import com.employeemanagement.pojo.EmployeePojo;
import com.employeemanagement.services.DepartmentService;
import com.employeemanagement.services.EmployeeService;
import com.employeemanagement.services.OperationalService;
import com.employeemanagement.utils.RegionEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeeServiceTest {
    private static Logger logger = LoggerFactory.getLogger(EmployeeServiceTest.class);

    @Autowired
    EmployeeService employeeService;

    @Autowired
    OperationalService operationalService;

    @Autowired
    DepartmentService departmentService;

    @BeforeEach
    public void setUpDepartment(){
        if(departmentService.findAll().isEmpty())
            operationalService.loadDepartmentData();
        logger.info("Department Loaded");
    }

    @Test
    void saveEmployeeWithoutUserNameAlreadyPresent() {
        EmployeePojo employeePojo = new EmployeePojo();
        employeePojo.setUsername("Existing");
        employeePojo.setPassword("Adnkd@123");
        employeePojo.setAge(35);
        employeePojo.setCtc(458600.35);
        employeePojo.setJoiningDate(LocalDate.parse("20-01-2017", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        employeePojo.setDeptName("Operations");
        employeePojo.setRegion(RegionEnum.ASIA.getValue());
        employeePojo.setCity("Mumbai");
        employeePojo.setPinCode("400001");
        employeePojo.setState("Maharashtra");
        employeePojo.setCountry("India");
        employeeService.saveEmployee(employeePojo);

        employeePojo.setCity("Thane");
        employeePojo.setPinCode("400401");
        String response = employeeService.saveEmployee(employeePojo);
        logger.info("Test Case : Username already exist");
        assertEquals("Failed To Save Employee : Employee name already exist.",response);
    }

    @Test
    void saveEmployeeWithoutUserName() {
        EmployeePojo employeePojo = new EmployeePojo();
        employeePojo.setPassword("Adnkd@123");
        employeePojo.setAge(35);
        employeePojo.setCtc(458600.35);
        employeePojo.setJoiningDate(LocalDate.parse("20-01-2017", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        employeePojo.setDeptName("IT");
        employeePojo.setRegion(RegionEnum.ASIA.getValue());
        employeePojo.setCity("Mumbai");
        employeePojo.setPinCode("400001");
        employeePojo.setState("Maharashtra");
        employeePojo.setCountry("India");
        String response = employeeService.saveEmployee(employeePojo);
        assertEquals("Failed To Save Employee : Username cannot be empty",response);
    }

    @Test
    void saveEmployeeWithoutPassword() {
        EmployeePojo employeePojo = new EmployeePojo();
        employeePojo.setUsername("abbd@sdk.com");
        employeePojo.setAge(35);
        employeePojo.setCtc(458600.35);
        employeePojo.setJoiningDate(LocalDate.parse("20-01-2017", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        employeePojo.setDeptName("IT");
        employeePojo.setRegion(RegionEnum.ASIA.getValue());
        employeePojo.setCity("Mumbai");
        employeePojo.setPinCode("400001");
        employeePojo.setState("Maharashtra");
        employeePojo.setCountry("India");
        String response = employeeService.saveEmployee(employeePojo);
        assertEquals("Failed To Save Employee : Password cannot be empty",response);
    }

}