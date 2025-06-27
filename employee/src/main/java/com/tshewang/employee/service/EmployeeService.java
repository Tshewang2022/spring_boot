package com.tshewang.employee.service;

import com.tshewang.employee.entities.Employee;
import com.tshewang.employee.request.EmployeeRequest;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
    Employee findById(long theId);
    Employee save(EmployeeRequest theEmployeeRequest);
    Employee update(long id, EmployeeRequest employeeRequest);
    Employee convertToEmployee(long id, EmployeeRequest employeeRequest);
    void deleteById(long theId);
}
