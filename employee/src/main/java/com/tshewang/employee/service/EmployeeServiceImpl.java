package com.tshewang.employee.service;


import com.tshewang.employee.dao.EmployeeRepository;
import com.tshewang.employee.entities.Employee;
import com.tshewang.employee.request.EmployeeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository theEmployeeRepository){
        employeeRepository = theEmployeeRepository;
    }

    @Override
    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(long theId) {
       Optional <Employee> result = employeeRepository.findById(theId);
       Employee theEmployee = null;
        if(result.isPresent()){
            theEmployee = result.get();
        }else{
            throw new RuntimeException("Did not find employee id "+ theId);
        }
        return theEmployee;
    }

    @Transactional
    @Override
    public Employee save(EmployeeRequest theEmployeeRequest) {
        Employee theEmployee = convertToEmployee(0, theEmployeeRequest);
            return employeeRepository.save(theEmployee);
    }

    @Transactional
    @Override
    public Employee update(long id, EmployeeRequest employeeRequest) {
        Employee theEmployee = convertToEmployee(id, employeeRequest);
        return employeeRepository.save(theEmployee);
    }

    @Override
    public Employee convertToEmployee(long id, EmployeeRequest employeeRequest) {
        return new Employee(id, employeeRequest.getFirstName(),
                employeeRequest.getLastName(),
                employeeRequest.getEmail());
    }

    @Transactional
    @Override
    public void deleteById(long theId) {
        employeeRepository.deleteById(theId);
    }
}
