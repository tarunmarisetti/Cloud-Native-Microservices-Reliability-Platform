package com.employee_service.service.client;

import com.employee_service.dto.DepartmentResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "department",fallback = DepartmentFallBack.class)
public interface DepartmentFeignClient {
    @GetMapping("departments/{departmentCode}")
    DepartmentResDto getDepartmentByCode(@RequestHeader(name = "myapp-correlation-id")String correlationId,
                                             @PathVariable("departmentCode") String code);

    @GetMapping("departments/validate/{code}")
    Boolean validateDepartmentCode(@PathVariable String code);
}


