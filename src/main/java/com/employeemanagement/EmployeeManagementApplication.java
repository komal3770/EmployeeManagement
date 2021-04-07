package com.employeemanagement;

import com.employeemanagement.pojo.EmployeePojo;
import com.employeemanagement.services.OperationalService;
import com.employeemanagement.utils.RegionEnum;
import com.employeemanagement.utils.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
@Configuration
public class EmployeeManagementApplication {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeManagementApplication.class);


	@Autowired
	private OperationalService operationalService;

	@PostConstruct
	public void init(){
		operationalService.loadDepartmentData();
		operationalService.loadEmployeeData();
	}

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementApplication.class, args);
	}
}
