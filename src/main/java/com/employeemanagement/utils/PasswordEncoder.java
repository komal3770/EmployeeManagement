package com.employeemanagement.utils;

import com.employeemanagement.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoder {
    private static Logger logger = LoggerFactory.getLogger(PasswordEncoder.class);
    private static MessageDigest md;

    static {
        try {
            if(md==null)
                md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String cryptWithMD5(String pass){
        try {
            byte[] passBytes = pass.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<digested.length;i++){
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        }
        catch (ArrayIndexOutOfBoundsException ex){
            logger.info("Exception for pass "+pass+" :: "+ex.getMessage());
        }
        return null;
    }

    public static boolean validate(String password, String encryptedPassword){
        String passwordEncrypt2 = cryptWithMD5(password);
        return passwordEncrypt2.equals(encryptedPassword);
    }
}
