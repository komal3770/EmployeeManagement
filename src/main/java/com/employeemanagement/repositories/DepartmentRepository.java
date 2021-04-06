package com.employeemanagement.repositories;

import com.employeemanagement.entities.Department;
import com.employeemanagement.utils.RegionEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Department repository.
 */
@Repository
public interface DepartmentRepository extends CrudRepository<Department, Integer> {

    Optional<Department> findByNameAndRegion(String name, RegionEnum regionEnum);

    List<Department> findByIdIn(List<Integer> ids);
}
