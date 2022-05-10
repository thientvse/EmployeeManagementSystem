package com.thientv7.employeemanagement.controller;

import com.thientv7.employeemanagement.exception.ResourceNotFoundException;
import com.thientv7.employeemanagement.model.Employees;
import com.thientv7.employeemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {


    @Autowired
    private EmployeeRepository employeeRepository;

    // get all employees
    @GetMapping("/employees")
    public List<Employees> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // create employee
    @PostMapping("/employees")
    public Employees createEmployee(@RequestBody Employees employees) {
        return employeeRepository.save(employees);
    }

    //get employee by id
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employees> getEmployeeById(@PathVariable Long id) {
        Employees employees = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :"+id));
        return ResponseEntity.ok(employees);
    }

    // update employee
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employees> updateEmployee(@PathVariable Long id,@RequestBody Employees employeesDetails){
        Employees employees = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :"+id));

        employees.setFirstName(employeesDetails.getFirstName());
        employees.setLastName(employeesDetails.getLastName());
        employees.setEmail(employeesDetails.getEmail());

        Employees updatedEmployee = employeeRepository.save(employees);

        return ResponseEntity.ok(updatedEmployee);
    }

    // delete employee by id
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
        Employees employees = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :"+id));

        employeeRepository.delete(employees);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deteted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
