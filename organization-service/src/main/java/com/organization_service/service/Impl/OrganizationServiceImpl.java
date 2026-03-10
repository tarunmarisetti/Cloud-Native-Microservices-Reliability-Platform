package com.organization_service.service.Impl;

import com.organization_service.dto.OrganizationReqDto;
import com.organization_service.dto.OrganizationResDto;
import com.organization_service.entity.Organization;
import com.organization_service.exceptions.ResourceNotFoundException;
import com.organization_service.exceptions.SQLConstraintViolationException;
import com.organization_service.repository.OrganizationRepository;
import com.organization_service.service.OrganizationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final ModelMapper modelMapper;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository, ModelMapper modelMapper) {
        this.organizationRepository = organizationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrganizationResDto saveOrganization(OrganizationReqDto organizationDto) throws SQLConstraintViolationException {
        if(organizationRepository.existsByOrganizationCode(organizationDto.getOrganizationCode())){
            throw new SQLConstraintViolationException(organizationDto.getOrganizationCode());
        }
        Organization organization=new Organization();
        organization.setOrganizationCode(organizationDto.getOrganizationCode());
        organization.setOrganizationName(organizationDto.getOrganizationName());
        organization.setOrganizationDescription(organizationDto.getOrganizationDescription());
        Organization savedOrganization=organizationRepository.save(organization);
        return modelMapper.map(savedOrganization, OrganizationResDto.class);
    }

    @Override
    public OrganizationResDto getOrganizationByCode(String code) {
        Organization organization=organizationRepository.findByOrganizationCode(code)
                .orElseThrow(()-> new ResourceNotFoundException("department","department-code",code));
        return modelMapper.map(organization, OrganizationResDto.class);
    }

    @Override
    public List<OrganizationResDto> getAllOrganizations() {
        return organizationRepository.findAll().stream()
                .map(organization -> modelMapper.map(organization, OrganizationResDto.class)).toList();
    }

    @Override
    public OrganizationResDto updateOrganization(Long id, OrganizationReqDto organizationDto) throws SQLConstraintViolationException {
        Organization organization=organizationRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("organization","id",id.toString()));
        if(Objects.equals(organization.getOrganizationCode(), organizationDto.getOrganizationCode())){
            throw new SQLConstraintViolationException(organizationDto.getOrganizationCode());
        }
        if(organizationDto.getOrganizationCode()!=null){
            organization.setOrganizationCode(organizationDto.getOrganizationCode());
        }
        if(organizationDto.getOrganizationName()!=null){
            organization.setOrganizationName(organizationDto.getOrganizationName());
        }
        if(organizationDto.getOrganizationDescription()!=null){
            organization.setOrganizationDescription(organizationDto.getOrganizationDescription());
        }
        Organization updatedDepartment=organizationRepository.save(organization);
        return modelMapper.map(updatedDepartment, OrganizationResDto.class);
    }

    @Override
    public void deleteOrganizationByCode(String code) {
        Organization organization=organizationRepository.findByOrganizationCode(code)
                .orElseThrow(()->new ResourceNotFoundException("organization","organization-code",code));
        organizationRepository.delete(organization);


    }
}
