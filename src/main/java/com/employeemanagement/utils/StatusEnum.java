package com.employeemanagement.utils;

public enum StatusEnum {
    INACTIVE(false),ACTIVE(true);

    private Boolean value = false;

    private StatusEnum(Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return this.value;
    }

    public static StatusEnum getByValue(Boolean value) {
        if(INACTIVE.value.equals(value)) {
            return INACTIVE;
        }
        else {
            return ACTIVE;
        }
    }
}
