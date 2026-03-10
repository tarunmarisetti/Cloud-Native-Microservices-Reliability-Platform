package com.organization_service.service;

import com.organization_service.dto.OrganizationReqDto;
import com.organization_service.dto.OrganizationResDto;
import com.organization_service.exceptions.SQLConstraintViolationException;

import java.util.List;

public interface OrganizationService {
    OrganizationResDto saveOrganization(OrganizationReqDto organizationDto)throws SQLConstraintViolationException;
    OrganizationResDto getOrganizationByCode(String code);
    List<OrganizationResDto> getAllOrganizations();
    OrganizationResDto updateOrganization(Long id, OrganizationReqDto organizationDto) throws SQLConstraintViolationException;
    void deleteOrganizationByCode(String code);
}
