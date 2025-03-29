package com.example.leavemanagement.service;

import com.example.leavemanagement.model.Employee;
import com.example.leavemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Employee createEmployee(Employee employee) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Employee employee) {
        if (employee.getPassword() != null && !employee.getPassword().isEmpty()) {
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        }
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Optional<Employee> getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    public Employee findByUsername(String username) {
        return employeeRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    public void updateLeaveBalance(Long employeeId, String leaveType, int days) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        
        switch (leaveType) {
            case "ANNUAL":
                employee.setAnnualLeaveBalance(employee.getAnnualLeaveBalance() - days);
                break;
            case "SICK":
                employee.setSickLeaveBalance(employee.getSickLeaveBalance() - days);
                break;
            case "PERSONAL":
                employee.setPersonalLeaveBalance(employee.getPersonalLeaveBalance() - days);
                break;
            default:
                throw new RuntimeException("Invalid leave type");
        }
        
        employeeRepository.save(employee);
    }
} 