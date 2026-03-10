package com.organization_service.mapper;

import com.organization_service.dto.OrganizationReqDto;
import com.organization_service.entity.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {
    public OrganizationReqDto mapToDto(Organization organization){
        return new OrganizationReqDto(
                organization.getOrganizationName(),
                organization.getOrganizationDescription(),
                organization.getOrganizationCode()
        );
    }
    public Organization mapToEntity(OrganizationReqDto organizationDto){
        Organization organization=new Organization();

        organization.setOrganizationName(organizationDto.getOrganizationName());
        organization.setOrganizationDescription(organizationDto.getOrganizationDescription());
        organization.setOrganizationCode(organizationDto.getOrganizationCode());

        return organization;
    }
}