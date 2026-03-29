package com.motocart.ciaas_microservice.util;

public class MaskingUtil {

    public String maskEmail(String email){
        int indexOfAt = email.indexOf("@");
        String beforeAt = email.substring(0, indexOfAt);
        StringBuilder maskedEmail = new StringBuilder();
        maskedEmail.append(beforeAt.charAt(0));
        maskedEmail.append("*".repeat(beforeAt.length() - 1));
        maskedEmail.append(email.substring(indexOfAt));
        return maskedEmail.toString();
    }
}
