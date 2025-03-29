package com.example.leavemanagement.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "leave_requests")
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String leaveType; // ANNUAL, SICK, PERSONAL

    @Column(nullable = false)
    private String status; // PENDING, APPROVED, REJECTED

    @Column(length = 500)
    private String reason;

    @Column(nullable = false)
    private Integer numberOfDays;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private Employee approvedBy;

    private String comments;
} 