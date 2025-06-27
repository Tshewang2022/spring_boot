package com.tshewang.employee.controller;

// this is how the api are being created using the spring api
import com.tshewang.employee.entities.Employee;
import com.tshewang.employee.request.EmployeeRequest;
import com.tshewang.employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Tag(name="Employee Rest API endpoint", description = "Operations related to employee")
public class EmployeeRestController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService theEmployeeService){
        employeeService = theEmployeeService;
    }

    @Operation(summary = "Get all employees", description = "Retrieve a list of all employees")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Employee> findAll(){
        return employeeService.findAll();
    }

    @Operation(summary = "Fetch single employee", description = "Get a single employee from database")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{employeeId}")
    public Employee getEmployee(@PathVariable @Min(value =1) long employeeId){
        Employee theEmployee = employeeService.findById(employeeId);
        return theEmployee;
    }

    @Operation(summary = "Create a new employee", description = "Add a new employee to database")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public Employee addEmployee(@Valid @RequestBody EmployeeRequest theEmployee){
        Employee dbEmployee = employeeService.save(theEmployee);
        return dbEmployee;
    }

    @Operation(summary = "Update an employee", description = "Update the current employee")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public Employee updateEmployee(@PathVariable @Min(value =1) long employeeId,
                                   @Valid @RequestBody EmployeeRequest employeeRequest){
        Employee dbEmployee = employeeService.update(employeeId, employeeRequest);
        return dbEmployee;
    }

    @Operation(summary = "Delete a employee", description = "Remove the employee from the database")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{employeeId}")
    public void deleteEmployee(@PathVariable @Min(value=1) long employeeId){
        employeeService.deleteById(employeeId);
    }

}
