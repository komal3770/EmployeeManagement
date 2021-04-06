package com.employeemanagement.pojo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The type Department pojo.
 */
public class DepartmentPojo implements Serializable {
    @NotNull(message = "Department Name is empty")
    @NotBlank(message = "Department Name is empty")
    private String name;

    @NotNull(message = "Region Name is empty")
    @NotBlank(message = "Region Name is empty")
    private String region;

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setName(String name) {
        this.name = name;
    }

}
