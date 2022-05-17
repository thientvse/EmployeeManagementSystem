package com.thientv7.employeemanagement.controller;

import com.thientv7.employeemanagement.exception.ResourceNotFoundException;
import com.thientv7.employeemanagement.model.Employees;
import com.thientv7.employeemanagement.repository.EmployeeRepository;
import com.thientv7.employeemanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    @Autowired
    private EmployeeService employeeService;

    // get all employees
    @GetMapping("/employees")
    public List<Employees> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // create employee
    @PostMapping("/employees")
    public Employees createEmployee(@RequestBody Employees employees) {
        return employeeService.createEmployee(employees);
    }

    //get employee by id
//    @Cacheable(value = "employee", key = "#id")
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employees> getEmployeeById(@PathVariable Long id) {
        Employees employees = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :"+id));
        return ResponseEntity.ok(employees);
    }

    // update employee
//    @CachePut(value = "employee", key = "#employee.id")
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employees> updateEmployee(@PathVariable Long id,@RequestBody Employees employeesDetails){
        Employees employees = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :"+id));

        employees.setFirstName(employeesDetails.getFirstName());
        employees.setLastName(employeesDetails.getLastName());
        employees.setEmail(employeesDetails.getEmail());

        Employees updatedEmployee = employeeService.updateEmployee(employees);

        return ResponseEntity.ok(updatedEmployee);
    }

    // delete employee by id
//    @CacheEvict(value = "employee", allEntries=true)
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
        Employees employees = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :"+id));

        employeeRepository.delete(employees);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deteted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
