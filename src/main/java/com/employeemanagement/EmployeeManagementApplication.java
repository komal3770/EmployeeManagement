package com.employeemanagement;

import com.employeemanagement.pojo.EmployeePojo;
import com.employeemanagement.services.OperationalService;
import com.employeemanagement.utils.RegionEnum;
import com.employeemanagement.utils.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class EmployeeManagementApplication {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeManagementApplication.class);

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext context = SpringApplication.run(EmployeeManagementApplication.class, args);
		//Initializing Employee Data Using CSV
		OperationalService dataInitializingService = context.getBean(OperationalService.class);
		dataInitializingService.loadDepartmentData();
		logger.info("Departments Loaded=====================================================================");
		dataInitializingService.loadEmployeeData();
		logger.info("Employees loaded========================================================================");
	}
}
