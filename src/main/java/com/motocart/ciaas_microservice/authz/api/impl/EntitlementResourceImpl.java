package com.motocart.ciaas_microservice.authz.api.impl;

import com.motocart.ciaas_microservice.authz.api.EntitlementResource;
import com.motocart.ciaas_microservice.authz.service.EntitlementAuthnService;
import com.motocart.library.common.annotation.MotocartAPI;
import com.motocart.library.common.dto.EntitlementsDTO;
import com.motocart.library.common.types.Permission;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@MotocartAPI("/entitlements")
public class EntitlementResourceImpl implements EntitlementResource {

    private final EntitlementAuthnService entitlementAuthnService;

    public EntitlementResourceImpl(EntitlementAuthnService entitlementAuthnService) {
        this.entitlementAuthnService = entitlementAuthnService;
    }

    @Override
    @GetMapping
    public EntitlementsDTO getAllEntitlementsForLoggedInUser() {
        return entitlementAuthnService.getAllEntitlementsForLoggedInUser();
    }

    @Override
    @GetMapping("/{userId}")
    public EntitlementsDTO getAllEntitlements(@PathVariable int userId) {
        entitlementAuthnService.canAccess(Permission.ENTITLEMENTS_VIEW);
        return entitlementAuthnService.getAllEntitlements(userId);
    }
}
