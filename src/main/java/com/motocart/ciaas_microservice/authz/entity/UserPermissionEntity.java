package com.motocart.ciaas_microservice.authz.entity;

import com.motocart.ciaas_microservice.types.PermissionEffect;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_permissions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_permission_id")
    private int userPermissionId;

    @Column(name = "user_id")
    private int userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", nullable = false)
    private PermissionEntity permission;

    @Column(name = "effect")
    @Enumerated(EnumType.STRING)
    private PermissionEffect effect;
}
