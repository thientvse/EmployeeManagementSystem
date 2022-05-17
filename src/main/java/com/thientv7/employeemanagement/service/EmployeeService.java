package com.thientv7.employeemanagement.service;

import com.thientv7.employeemanagement.model.Employees;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface EmployeeService {

    List<Employees> getAllEmployees();

    Employees createEmployee(Employees employees);
    Optional<Employees> getEmployeeById(Long id);
    Employees updateEmployee(Employees employees);
    void deleteEmployee(Employees employees);

}
