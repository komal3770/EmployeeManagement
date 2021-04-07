package com.employeemanagement.services;

import com.employeemanagement.entities.Department;
import com.employeemanagement.pojo.DepartmentPojo;
import com.employeemanagement.repositories.DepartmentRepository;
import com.employeemanagement.utils.RegionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
/**
 * The type Department service.
 */
@Service
public class DepartmentService {
    private static Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * Save department string.
     *
     * @param departmentPojo the department data to be saved
     * @return the response in String
    */
    public String saveDepartment(DepartmentPojo departmentPojo){
        try{
            String validationMessage = validateDepartment(departmentPojo);
            if(validationMessage!=null && !validationMessage.isEmpty())
                return "Failed to save department :: "+validationMessage;
            Department department = new Department();
            department.setName(departmentPojo.getName());
            department.setRegion(RegionEnum.getValueFromString(departmentPojo.getRegion()));
            departmentRepository.save(department);
            logger.debug("Department Saved Successfully "+departmentPojo.getName());
            return "Department Saved Successfully";
        }
        catch (Exception ex){
            ex.printStackTrace();
            logger.error("Failed saveDepartment : "+ex.getMessage());
            return "Failed to save department :: "+ex.getMessage();
        }
    }

    /**
     * Validate department string.
     *
     * @param departmentPojo the department pojo
     * @return the response in String
    */
    public String validateDepartment(DepartmentPojo departmentPojo){
        //Create ValidatorFactory which returns validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

        //It validates bean instances
        Validator validator = factory.getValidator();

        //Validate bean
        Set<ConstraintViolation<DepartmentPojo>> constraintViolations = validator.validate(departmentPojo);
        //Show errors
        String errorMessages = null;
        if (constraintViolations!=null && !constraintViolations.isEmpty()) {
            for (ConstraintViolation<DepartmentPojo> violation : constraintViolations) {
                errorMessages=violation.getMessage();
                break;
            }
        }

        if(errorMessages == null){
            Optional<Department> department = departmentRepository.findByNameAndRegion(departmentPojo.getName(), RegionEnum.getValueFromString(departmentPojo.getRegion()));
            if(department.isPresent())
                errorMessages="Department in the region already exist.";
        }
        return errorMessages;
    }

    /**
     * Find by name and region optional.
     *
     * @param name       the name
     * @param regionEnum the region enum
     * @return the optional object of department
     */
    public Optional<Department> findByNameAndRegion(String name, RegionEnum regionEnum){
        return departmentRepository.findByNameAndRegion(name, regionEnum);
    }

    /**
     * Find by department ids list.
     *
     * @param ids the ids
     * @return the list of department
     */
    public List<Department> findByDepartmentIds(List<Integer> ids){
        return departmentRepository.findByIdIn(ids);
    }

    /**
     * Find all list.
     *
     * @return the list of Department
     */
    public List<Department> findAll(){
        List<Department> departmentList = new ArrayList<>();
        Iterable<Department> departmentIterable = departmentRepository.findAll();
        departmentIterable.forEach(departmentList::add);
        return departmentList;
    }
}
