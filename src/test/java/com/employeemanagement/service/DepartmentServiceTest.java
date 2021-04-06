package com.employeemanagement.service;

import com.employeemanagement.pojo.DepartmentPojo;
import com.employeemanagement.services.DepartmentService;
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

    @BeforeEach
    void setUp() {

    }

    @Test
    void saveDepartmentTest() {
        DepartmentPojo departmentPojo = new DepartmentPojo();
        String response = departmentService.saveDepartment(departmentPojo);
        assertEquals("Failed to save department :: Department Name is empty",response);
    }
}