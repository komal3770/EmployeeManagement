package com.employeemanagement.service;

import com.employeemanagement.pojo.DepartmentPojo;
import com.employeemanagement.services.DepartmentService;
import com.employeemanagement.services.OperationalService;
import com.employeemanagement.utils.RegionEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
class DepartmentServiceTest {

    @Autowired
    DepartmentService departmentService;

    @Autowired
    OperationalService operationalService;

    @BeforeEach
    void setUp() {
        if(departmentService.findAll().isEmpty())
            operationalService.loadDepartmentData();
    }

    @Test
    void saveDepartmentNameTest() {
        DepartmentPojo departmentPojo = new DepartmentPojo();
        departmentPojo.setRegion(RegionEnum.EUROPE.getValue());
        String response = departmentService.saveDepartment(departmentPojo);
        assertEquals("Failed to save department :: Department Name is empty",response);
    }

    @Test
    void saveDepartmentRegionTest() {
        DepartmentPojo departmentPojo = new DepartmentPojo();
        departmentPojo.setName("IT");
        String response = departmentService.saveDepartment(departmentPojo);
        assertEquals("Failed to save department :: Region Name is empty",response);
    }

    @Test
    void saveDepartmentDuplicateTest() {
        DepartmentPojo departmentPojo = new DepartmentPojo();
        departmentPojo.setName("Accounting");
        departmentPojo.setRegion(RegionEnum.ASIA.getValue());
        String response = departmentService.saveDepartment(departmentPojo);
        assertEquals("Failed to save department :: Department in the region already exist.",response);
    }
}