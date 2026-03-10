package com.department_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Entity
@Table(name = "departments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String departmentName;
    @Column
    private String departmentDescription;
    @Column
    private String departmentCode;
}
