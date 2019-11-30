package com.example.springdemo.controller;

import com.example.springdemo.exception.ResourceNotFoundException;
import com.example.springdemo.model.Employee;
import com.example.springdemo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @PostMapping("/employees")
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable(value = "id") Long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
    }

    @PutMapping("/employees/{id}")
    public Employee updateNote(@PathVariable(value = "id") Long employeeId,
                           @Valid @RequestBody Employee employeeDetails) throws ParseException {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));

        employee.setName(employeeDetails.getName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setSalary(employeeDetails.getSalary());
        employee.setDob(employeeDetails.getDob());

        Employee updatedEmployee = employeeRepository.save(employee);
        return updatedEmployee;
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));

        employeeRepository.delete(employee);

        return ResponseEntity.ok().build();
    }
}