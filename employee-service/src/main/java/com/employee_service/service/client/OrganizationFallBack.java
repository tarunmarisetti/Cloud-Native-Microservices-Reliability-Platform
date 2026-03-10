package com.employee_service.service.client;

import com.employee_service.dto.OrganizationResDto;
import org.springframework.stereotype.Component;

@Component
public class OrganizationFallBack implements OrganizationFeignClient{
    @Override
    public OrganizationResDto getOrganizationByCode(String correlationId, String code) {
        return null;
    }

    @Override
    public Boolean validateOrganizationCode(String code) {
        return Boolean.FALSE;
    }
}
