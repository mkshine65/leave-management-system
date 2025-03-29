package com.example.leavemanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

@Controller
public class DashboardController {

    @GetMapping("/my-leave-requests")
    public String myLeaveRequests(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("currentUser", userDetails);
        return "leave/my-requests";
    }

    @GetMapping("/pending-requests")
    public String pendingRequests(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("currentUser", userDetails);
        return "leave/pending-requests";
    }

    @GetMapping("/employee-list")
    public String employees(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("currentUser", userDetails);
        return "employees/list";
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("currentUser", userDetails);
        return "profile/view";
    }

    @GetMapping("/holiday-calendar")
    public String holidayCalendar(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("currentUser", userDetails);
        return "calendar/holidays";
    }
} 