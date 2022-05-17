package com.thientv7.employeemanagement.service.impl;

import com.thientv7.employeemanagement.exception.ResourceNotFoundException;
import com.thientv7.employeemanagement.model.Employees;
import com.thientv7.employeemanagement.repository.EmployeeRepository;
import com.thientv7.employeemanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public List<Employees> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employees createEmployee(Employees employees) {
        return employeeRepository.save(employees);
    }

    @Override
    public Optional<Employees> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employees updateEmployee(Employees employees) {
        return employeeRepository.save(employees);
    }

    @Override
    public void deleteEmployee(Employees employees) {
        employeeRepository.delete(employees);
    }
}
