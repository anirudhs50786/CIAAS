package com.motocart.ciaas_microservice.types;

public class Profile {

    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }

    public enum AddressType {
        HOME,
        WORK,
        OTHER
    }

    public enum Country {
        INDIA(0, "India"),
        UNITED_KINGDOM(1, "United Kingdom"),
        USA(2, "United States of America");

        private int countryId;
        private String displayName;

        Country(int countryId, String displayName) {
            this.countryId = countryId;
            this.displayName = displayName;
        }
    }
}
