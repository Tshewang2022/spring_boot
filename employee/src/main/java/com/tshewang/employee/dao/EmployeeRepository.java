package com.tshewang.employee.dao;

import com.tshewang.employee.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // that's it... not need to write any code LOL
}
