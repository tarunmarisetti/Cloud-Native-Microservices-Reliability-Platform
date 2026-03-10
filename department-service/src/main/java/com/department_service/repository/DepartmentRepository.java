package com.department_service.repository;

import com.department_service.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
    Optional<Department> findByDepartmentCode(String code);
    boolean existsByDepartmentCode(String code);
}
