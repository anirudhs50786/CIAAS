package com.motocart.ciaas_microservice.authz.api;

import com.motocart.library.common.dto.EntitlementsDTO;

public interface EntitlementResource {

    EntitlementsDTO getAllEntitlementsForLoggedInUser();

    EntitlementsDTO getAllEntitlements(int userId);
}
