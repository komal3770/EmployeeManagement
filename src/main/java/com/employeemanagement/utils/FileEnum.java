package com.employeemanagement.utils;

public class FileEnum {
    /**
     * The enum Employee file header.
     */
    public enum EmployeeFileHeader {
        NAME("Username"),
        PASSWORD("Password"),
        AGE("Age"),
        ANNUAL_CTC("AnnualCtc"),
        DEPARTMENT_NAME("DepartmentName"),
        CITY("City"),
        STATE("State"),
        PINCODE("Pincode"),
        COUNTRY("Country"),
        REGION("Region"),
        JOINING_DATE("JoiningDate"),
        STATUS("Status");

        private final String value;

        EmployeeFileHeader(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    /**
     * The enum Department file header.
     */
    public enum DepartmentFileHeader {

        DEPT_NAME("Department Name"),
        REGION("Region"),
        STATUS("Status");

        private final String value;

        DepartmentFileHeader(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
}
