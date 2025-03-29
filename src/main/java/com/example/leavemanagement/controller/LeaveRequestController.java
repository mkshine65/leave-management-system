package com.example.leavemanagement.controller;

import com.example.leavemanagement.model.LeaveRequest;
import com.example.leavemanagement.model.Employee;
import com.example.leavemanagement.service.LeaveRequestService;
import com.example.leavemanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"/leave-requests", "/leavemanagement"})
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee currentUser = employeeService.findByUsername(auth.getName());
        
        model.addAttribute("leaveRequest", new LeaveRequest()); // Add this for the form
        model.addAttribute("leaveRequests", leaveRequestService.getLeaveRequestsByEmployee(currentUser));
        model.addAttribute("leaveTypes", leaveRequestService.getLeaveTypes());
        return "leave/dashboard";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("leaveRequest", new LeaveRequest());
        return "leave/form";
    }

    @PostMapping
    public String createLeaveRequest(@ModelAttribute LeaveRequest leaveRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee currentUser = employeeService.findByUsername(auth.getName());
        leaveRequest.setEmployee(currentUser);
        
        if (leaveRequestService.hasEnoughLeaveBalance(currentUser, leaveRequest.getLeaveType(), leaveRequest.getNumberOfDays())) {
            leaveRequestService.createLeaveRequest(leaveRequest);
            return "redirect:/leave-requests";
        }
        return "redirect:/leave-requests/new?error=insufficient_balance";
    }

    @GetMapping("/{id}")
    public String viewLeaveRequest(@PathVariable Long id, Model model) {
        model.addAttribute("leaveRequest", leaveRequestService.getLeaveRequestById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found")));
        return "leave/view";
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('MANAGER')")
    public String listPendingRequests(Model model) {
        model.addAttribute("leaveRequests", leaveRequestService.getPendingLeaveRequests());
        return "leave/pending";
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasRole('MANAGER')")
    public String approveLeaveRequest(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee approver = employeeService.findByUsername(auth.getName());
        leaveRequestService.approveLeaveRequest(id, approver);
        return "redirect:/leave-requests/pending";
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasRole('MANAGER')")
    public String rejectLeaveRequest(@PathVariable Long id, @RequestParam String comments) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee approver = employeeService.findByUsername(auth.getName());
        leaveRequestService.rejectLeaveRequest(id, approver, comments);
        return "redirect:/leave-requests/pending";
    }

    @GetMapping("/calendar")
    public String showLeaveCalendar(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee currentUser = employeeService.findByUsername(auth.getName());
        
        model.addAttribute("leaveRequests", leaveRequestService.getAllLeaveRequests());
        model.addAttribute("currentUser", currentUser);
        return "leave/calendar";
    }
} 