package com.motocart.ciaas_microservice.authz.api.impl;

import com.motocart.ciaas_microservice.authz.api.EntitlementResource;
import com.motocart.ciaas_microservice.authz.service.EntitlementService;
import com.motocart.library.common.annotation.MotocartAPI;
import com.motocart.library.common.dto.EntitlementsDTO;
import com.motocart.library.common.types.Permission;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@MotocartAPI("/entitlements")
public class EntitlementResourceImpl implements EntitlementResource {

    private final EntitlementService entitlementService;

    public EntitlementResourceImpl(EntitlementService entitlementService) {
        this.entitlementService = entitlementService;
    }

    @Override
    @GetMapping
    public EntitlementsDTO getAllEntitlementsForLoggedInUser() {
        return entitlementService.getAllEntitlementsForLoggedInUser();
    }

    @Override
    @GetMapping("/{userId}")
    public EntitlementsDTO getAllEntitlements(@PathVariable int userId) {
        entitlementService.canAccess(Permission.ENTITLEMENTS_VIEW);
        return entitlementService.getAllEntitlements(userId);
    }
}
