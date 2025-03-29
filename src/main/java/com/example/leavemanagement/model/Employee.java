package com.example.leavemanagement.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // ROLE_EMPLOYEE or ROLE_MANAGER

    @OneToMany(mappedBy = "employee")
    private List<LeaveRequest> leaveRequests;

    @Column(nullable = false)
    private Integer annualLeaveBalance = 20; // Default annual leave days

    @Column(nullable = false)
    private Integer sickLeaveBalance = 10; // Default sick leave days

    @Column(nullable = false)
    private Integer personalLeaveBalance = 5; // Default personal leave days
} 