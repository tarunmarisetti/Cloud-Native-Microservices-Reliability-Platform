package com.employee_service.service.client;

import com.employee_service.dto.DepartmentResDto;
import org.springframework.stereotype.Component;

@Component
public class DepartmentFallBack implements DepartmentFeignClient{
    @Override
    public DepartmentResDto getDepartmentByCode(String correlationId, String code) {
        return null;
    }

    @Override
    public Boolean validateDepartmentCode(String code) {
        return Boolean.FALSE;
    }
}
