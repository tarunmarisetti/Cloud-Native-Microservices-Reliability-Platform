package com.organization_service.repository;

import com.organization_service.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization,Long> {
    Optional<Organization> findByOrganizationCode(String code);
    boolean existsByOrganizationCode(String code);
}
