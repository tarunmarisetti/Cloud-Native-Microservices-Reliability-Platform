package com.employee_service.service.client;

import com.employee_service.dto.OrganizationResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "organization",fallback = OrganizationFallBack.class)
public interface OrganizationFeignClient {

    @GetMapping("organizations/{organizationCode}")
    OrganizationResDto getOrganizationByCode(
                        @RequestHeader(name = "myapp-correlation-id")String correlationId,
                        @PathVariable("organizationCode") String code);

    @GetMapping("organizations/validate/{code}")
    Boolean validateOrganizationCode(@PathVariable String code);
}
