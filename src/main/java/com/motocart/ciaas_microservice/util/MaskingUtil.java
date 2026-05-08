package com.motocart.ciaas_microservice.util;

public class MaskingUtil {

    public String maskEmail(String email){
        int indexOfAt = email.indexOf("@");
        String beforeAt = email.substring(0, indexOfAt);
        return beforeAt.charAt(0) +
                "*".repeat(beforeAt.length() - 1) +
                email.substring(indexOfAt);
    }
}
