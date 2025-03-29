package com.example.leavemanagement.service;

import com.example.leavemanagement.model.LeaveRequest;
import com.example.leavemanagement.model.Employee;
import com.example.leavemanagement.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class LeaveRequestService {
    
    @Autowired
    private LeaveRequestRepository leaveRequestRepository;
    
    @Autowired
    private EmployeeService employeeService;

    public LeaveRequest createLeaveRequest(LeaveRequest leaveRequest) {
        // Calculate number of days
        long days = ChronoUnit.DAYS.between(leaveRequest.getStartDate(), leaveRequest.getEndDate()) + 1;
        leaveRequest.setNumberOfDays((int) days);
        
        // Set initial status
        leaveRequest.setStatus("PENDING");
        
        return leaveRequestRepository.save(leaveRequest);
    }

    public LeaveRequest updateLeaveRequest(LeaveRequest leaveRequest) {
        return leaveRequestRepository.save(leaveRequest);
    }

    public void deleteLeaveRequest(Long id) {
        leaveRequestRepository.deleteById(id);
    }

    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    public Optional<LeaveRequest> getLeaveRequestById(Long id) {
        return leaveRequestRepository.findById(id);
    }

    public List<LeaveRequest> getLeaveRequestsByEmployee(Employee employee) {
        return leaveRequestRepository.findByEmployee(employee);
    }

    public List<LeaveRequest> getPendingLeaveRequests() {
        return leaveRequestRepository.findByStatus("PENDING");
    }

    public LeaveRequest approveLeaveRequest(Long requestId, Employee approver) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        
        // Update leave balance
        employeeService.updateLeaveBalance(
            leaveRequest.getEmployee().getId(),
            leaveRequest.getLeaveType(),
            leaveRequest.getNumberOfDays()
        );
        
        leaveRequest.setStatus("APPROVED");
        leaveRequest.setApprovedBy(approver);
        
        return leaveRequestRepository.save(leaveRequest);
    }

    public LeaveRequest rejectLeaveRequest(Long requestId, Employee approver, String comments) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        
        leaveRequest.setStatus("REJECTED");
        leaveRequest.setApprovedBy(approver);
        leaveRequest.setComments(comments);
        
        return leaveRequestRepository.save(leaveRequest);
    }

    public boolean hasEnoughLeaveBalance(Employee employee, String leaveType, int days) {
        switch (leaveType) {
            case "ANNUAL":
                return employee.getAnnualLeaveBalance() >= days;
            case "SICK":
                return employee.getSickLeaveBalance() >= days;
            case "PERSONAL":
                return employee.getPersonalLeaveBalance() >= days;
            default:
                return false;
        }
    }

    public List<String> getLeaveTypes() {
        return List.of("ANNUAL", "SICK", "PERSONAL");
    }
} 