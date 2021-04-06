package com.employeemanagement.utils;

import java.util.Arrays;
import java.util.Optional;

public enum RegionEnum {
    NORTH_AMERICA("North America"),ASIA("Asia"), EUROPE("Europe");

    private String value;

    public String getValue(){
        return this.value;
    }

    RegionEnum(String value){
        this.value=value;
    }

    public static RegionEnum getValueFromString(String value){
        Optional<RegionEnum> regionEnum = Arrays.stream(RegionEnum.values()).filter(r -> r.getValue().equals(value)).findFirst();
        return regionEnum.isPresent() ? regionEnum.get() : null;
    }
}
