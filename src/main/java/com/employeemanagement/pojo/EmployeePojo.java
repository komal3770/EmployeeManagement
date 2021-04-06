package com.employeemanagement.pojo;


import com.employeemanagement.utils.RegionEnum;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * The type Employee pojo.
 */
public class EmployeePojo implements Serializable {

    @NotNull(message = "Username cannot be empty")
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @NotNull(message = "Password cannot be empty")
    @NotBlank(message = "Password cannot be empty")
    private String password;

    @NotNull(message = "Age cannot be empty")
    @Min(value = 18,message = "Age should be minimum 18")
    private Integer age;

    @NotNull(message = "CTC cannot be empty")
    private Double ctc;

    @NotNull(message = "Department name cannot be empty")
    @NotBlank(message = "Department cannot be empty")
    private String deptName;

    @NotNull(message = "Joining date cannot be empty")
    private LocalDate joiningDate;

    @NotNull(message = "City cannot be empty")
    @NotBlank(message = "City cannot be empty")
    private String city;

    @NotNull(message = "State cannot be empty")
    @NotBlank(message = "State cannot be empty")
    private String state;

    @NotNull(message = "Country cannot be empty")
    @NotBlank(message = "Country cannot be empty")
    private String country;

    @NotNull(message = "Pincode cannot be empty")
    @NotBlank(message = "Pincode cannot be empty")
    private String pinCode;

    @NotNull(message = "Region cannot be empty")
    @NotBlank(message = "Region cannot be empty")
    private String region;

    private Boolean status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getCtc() {
        return ctc;
    }

    public void setCtc(Double ctc) {
        this.ctc = ctc;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "EmployeePojo{" +
                "username='" + username + '\'' +
                ", deptName='" + deptName + '\'' +
                ", region='" + region + '\'' +
                ", status=" + status +
                '}';
    }
}
