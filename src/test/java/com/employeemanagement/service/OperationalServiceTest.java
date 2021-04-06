package com.employeemanagement.service;

import com.employeemanagement.pojo.EmployeePojo;
import com.employeemanagement.services.DepartmentService;
import com.employeemanagement.services.OperationalService;
import com.employeemanagement.utils.StatusEnum;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
class OperationalServiceTest {
    private static Logger logger = LoggerFactory.getLogger(OperationalServiceTest.class);

    @Autowired
    OperationalService operationalService;

    @Autowired
    DepartmentService departmentService;

    @BeforeEach
    public void setUpDepartment(){
        if(departmentService.findAll().isEmpty())
            operationalService.loadDepartmentData();
        operationalService.loadEmployeeData();
    }

    @Test
    void findAllByDepartmentsAndCityAndCtcTest() {
        List<EmployeePojo> employeePojoList = operationalService.findAllByDepartmentsAndCityAndCtc("Mumbai",200000.0,500000.0);
        assertEquals(5,employeePojoList.size());
    }

    @Test
    void findAllByDepartmentAndAgeTest() {
        List<EmployeePojo> employeePojoList = operationalService.findAllByDepartmentAndAge("Operations",40, StatusEnum.ACTIVE);
        assertEquals(1,employeePojoList.size());
    }

    @Test
    void findAllByDepartmentAndAgeAndCtcAndStatusTest(){
        List<EmployeePojo> employeePojoList = operationalService.findAllByDepartmentAndAgeAndCtcAndStatus("Operations",25,10000.0,StatusEnum.ACTIVE);
        assertEquals(9,employeePojoList.size());
    }

}