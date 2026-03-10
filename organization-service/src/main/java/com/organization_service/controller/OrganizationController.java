package com.organization_service.controller;

import com.organization_service.dto.OrganizationContactInfoDto;
import com.organization_service.dto.OrganizationReqDto;
import com.organization_service.dto.OrganizationResDto;
import com.organization_service.exceptions.SQLConstraintViolationException;
import com.organization_service.repository.OrganizationRepository;
import com.organization_service.service.Impl.OrganizationServiceImpl;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    private final OrganizationServiceImpl organizationService;
    private final OrganizationRepository organizationRepository;
    private final OrganizationContactInfoDto organizationContactInfoDto;

    private static final Logger LOGGER= LoggerFactory.getLogger(OrganizationController.class);

    public OrganizationController(OrganizationServiceImpl organizationService, OrganizationRepository organizationRepository, OrganizationContactInfoDto organizationContactInfoDto) {
        this.organizationService = organizationService;
        this.organizationRepository = organizationRepository;
        this.organizationContactInfoDto = organizationContactInfoDto;
    }

    @PostMapping
    public ResponseEntity<OrganizationResDto> saveOrganization(@Valid @RequestBody OrganizationReqDto organizationDto) throws SQLConstraintViolationException {
        OrganizationResDto savedOrganization=organizationService.saveOrganization(organizationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrganization);
    }
    @GetMapping("/{code}")
    public ResponseEntity<OrganizationResDto> getOrganizationByCode(@RequestHeader(name = "myapp-correlation-id")String correlationId,
                                                                    @PathVariable("code") String code){
//        LOGGER.debug("myapp-correlation-id found :{}",correlationId);
        LOGGER.debug("getOrganizationDetails Method starts");
        OrganizationResDto savedOrganization=organizationService.getOrganizationByCode(code);
        LOGGER.debug("getOrganizationDetails Method ends");
        return ResponseEntity.status(HttpStatus.OK).body(savedOrganization);
    }
    @GetMapping()
    public ResponseEntity<List<OrganizationResDto>> getAllOrganizations(){
        List<OrganizationResDto> organizationDtos=organizationService.getAllOrganizations();
        return new ResponseEntity<>(organizationDtos,HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<OrganizationResDto> updateOrganization(@PathVariable("id") Long id, @RequestBody OrganizationReqDto organizationDto) throws SQLConstraintViolationException {
        OrganizationResDto updateDepartment=organizationService.updateOrganization(id,organizationDto);
        return new ResponseEntity<>(updateDepartment,HttpStatus.OK);
    }

    @DeleteMapping("{departmentCode}")
    public ResponseEntity<String> deleteOrganizationByCode(@PathVariable("departmentCode") String code){
        organizationService.deleteOrganizationByCode(code);
        return new ResponseEntity<>("record deleted",HttpStatus.OK);
    }

    @RateLimiter(name ="organizationContactInfo",fallbackMethod = "rateLimiterFallback")
    @GetMapping("/organizationContactInfo")
    public ResponseEntity<OrganizationContactInfoDto> getServiceDetails(){
        return new ResponseEntity<>(organizationContactInfoDto,HttpStatus.OK);
    }
    public ResponseEntity<String> rateLimiterFallback(Throwable t) {
        return new ResponseEntity<>("Too many requests. Please try again later.",HttpStatus.TOO_MANY_REQUESTS);
    }

    @GetMapping("/validate/{code}")
    public ResponseEntity<Boolean> validateOrganizationCode(@PathVariable String code) {
        boolean exists = organizationRepository.existsByOrganizationCode(code);
        return ResponseEntity.ok(exists);
    }

}
