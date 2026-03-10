package com.organization_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "organizations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String organizationName;
    private String organizationDescription;
    @Column(nullable = false,unique = true)
    private String organizationCode;
    @CreationTimestamp
    @Column(name = "createdDate", updatable = false)
    private LocalDateTime createdDate;
}
