package com.example.leavemanagement.controller;

import com.example.leavemanagement.model.Employee;
import com.example.leavemanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    @PreAuthorize("hasRole('MANAGER')")
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employee/list";
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('MANAGER')")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee/form";
    }

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public String createEmployee(@ModelAttribute Employee employee) {
        employeeService.createEmployee(employee);
        return "redirect:/employees";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasRole('MANAGER')")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.getEmployeeById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found")));
        return "employee/form";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public String updateEmployee(@PathVariable Long id, @ModelAttribute Employee employee) {
        employee.setId(id);
        employeeService.updateEmployee(employee);
        return "redirect:/employees";
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasRole('MANAGER')")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        // TODO: Get current user from security context
        return "employee/profile";
    }
} 