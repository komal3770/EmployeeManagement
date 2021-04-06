package com.employeemanagement.services;

import com.employeemanagement.entities.Address;
import com.employeemanagement.entities.Department;
import com.employeemanagement.entities.Employee;
import com.employeemanagement.pojo.EmployeePojo;
import com.employeemanagement.repositories.EmployeeRepository;
import com.employeemanagement.utils.PasswordEncoder;
import com.employeemanagement.utils.RegionEnum;
import com.employeemanagement.utils.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Employee com.employeemanagement.service.
 */
@Service
public class EmployeeService {
    private static Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentService departmentService;

    /**
     * Validates & Save employee object
     * */
    public String saveEmployee(EmployeePojo employeePojo){
        try{
            String validationMessage = validateEmployee(employeePojo);
            if(validationMessage != null && !validationMessage.isEmpty()){
                return "Failed To Save Employee : "+validationMessage;
            }

            Employee employee = new Employee();

            //Set Department Object
            Optional<Department> department = departmentService.findByNameAndRegion(employeePojo.getDeptName(), RegionEnum.getValueFromString(employeePojo.getRegion()));
            if(!department.isPresent()){
                return "Invalid Department";
            }
            employee.setDepartment(department.get());
            employee.setUsername(employeePojo.getUsername());
            employee.setPassword(PasswordEncoder.cryptWithMD5(employeePojo.getPassword()));
            employee.setAge(employeePojo.getAge());
            employee.setCtc(employeePojo.getCtc());
            employee.setJoiningDate(employeePojo.getJoiningDate());
            employee.setStatus(StatusEnum.ACTIVE);
            //Set Address Object
            Address address = new Address();
            address.setCity(employeePojo.getCity());
            address.setCountry(employeePojo.getCountry());
            address.setState(employeePojo.getState());
            address.setPinCode(employeePojo.getPinCode());
            employee.setAddress(address);

            employeeRepository.save(employee);
            return "Employee Saved Successfully";
        }
        catch (Exception ex){
            ex.printStackTrace();
            logger.info("Failed saveEmployee : "+ex.getMessage());
            return "Failed to save employee";
        }
    }

    /**
     * Employee validation
     * */
    public String validateEmployee(EmployeePojo employeePojo){
        //Create ValidatorFactory which returns validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

        //It validates bean instances
        Validator validator = factory.getValidator();

        //Validate bean
        Set<ConstraintViolation<EmployeePojo>> constraintViolations = validator.validate(employeePojo);
        //Show errors
        String errorMessages = null;
        if (constraintViolations!=null && !constraintViolations.isEmpty()) {
            for (ConstraintViolation<EmployeePojo> violation : constraintViolations) {
                errorMessages = violation.getMessage();
                break;
            }
        }

        if(errorMessages==null){
            Optional<Employee> employee = employeeRepository.findByUsername(employeePojo.getUsername());
            if(employee.isPresent()) {
                errorMessages = "Employee name already exist.";
            }
        }
        return errorMessages;
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<Employee> findAll(){
        return (List<Employee>) employeeRepository.findAll();
    }

}
