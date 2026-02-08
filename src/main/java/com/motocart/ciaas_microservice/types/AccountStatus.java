package com.motocart.ciaas_microservice.types;

import lombok.Getter;

@Getter
public enum AccountStatus {

    /**
     * Indicates the account is in active and needs support from admin user.
     **/
    INACTIVE(0),
    /**
     * Indicates the account is active.
     **/
    ACTIVE(1),
    /**
     * Indicates the account is active but the user profile is incomplete.
     **/
    ACTIVE_INCOMPLETE(2);

    private final int statusCode;

    AccountStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    public static boolean isActive(int statusCode) {
        return statusCode == ACTIVE.getStatusCode() || statusCode == ACTIVE_INCOMPLETE.getStatusCode();
    }
}
