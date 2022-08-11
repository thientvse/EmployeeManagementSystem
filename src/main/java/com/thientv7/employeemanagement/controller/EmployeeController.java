package com.thientv7.employeemanagement.controller;

import com.thientv7.employeemanagement.exception.ResourceNotFoundException;
import com.thientv7.employeemanagement.model.Employees;
import com.thientv7.employeemanagement.repository.EmployeeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Operation(description = "Get all employees",
              responses = @ApiResponse(
                      content = @Content(array = @ArraySchema(schema = @Schema(implementation = Employees.class))),
    responseCode = "200"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Employees.class)), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực",  content = @Content(schema = @Schema(implementation = Exception.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Truy cập bị cấm",  content = @Content(schema = @Schema(implementation = Exception.class), mediaType = "application/json"))})
    @GetMapping("/employees")
    public List<Employees> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // create employee
    @Operation(description = "create employee",
            responses = @ApiResponse(
                    content = @Content(schema = @Schema(implementation = Employees.class)),
                    responseCode = "200"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công", content = @Content(schema = @Schema(implementation = Employees.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực",  content = @Content(schema = @Schema(implementation = Exception.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Truy cập bị cấm",  content = @Content(schema = @Schema(implementation = Exception.class), mediaType = "application/json"))})
    @PostMapping("/employees")
    public Employees createEmployee(@RequestBody Employees employees) {
        return employeeRepository.save(employees);
    }

    //get employee by id
    @Operation(description = "get employee by id",
            responses = @ApiResponse(
                    content = @Content(schema = @Schema(implementation = Employees.class)),
                    responseCode = "200"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công", content = @Content(schema = @Schema(implementation = Employees.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực",  content = @Content(schema = @Schema(implementation = Exception.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Truy cập bị cấm",  content = @Content(schema = @Schema(implementation = Exception.class), mediaType = "application/json"))})
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employees> getEmployeeById(@PathVariable Long id) {
        Employees employees = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :"+id));
        return ResponseEntity.ok(employees);
    }

    // update employee
    @Operation(description = "update employee",
            responses = @ApiResponse(
                    content = @Content(schema = @Schema(implementation = Employees.class)),
                    responseCode = "200"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công", content = @Content(schema = @Schema(implementation = Employees.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực",  content = @Content(schema = @Schema(implementation = Exception.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Truy cập bị cấm",  content = @Content(schema = @Schema(implementation = Exception.class), mediaType = "application/json"))})
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
    @Operation(description = "delete employee by id",
            responses = @ApiResponse(
                    content = @Content(schema = @Schema(implementation = Map.class)),
                    responseCode = "200"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công", content = @Content(schema = @Schema(implementation = Employees.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực",  content = @Content(schema = @Schema(implementation = Exception.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Truy cập bị cấm",  content = @Content(schema = @Schema(implementation = Exception.class), mediaType = "application/json"))})
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
