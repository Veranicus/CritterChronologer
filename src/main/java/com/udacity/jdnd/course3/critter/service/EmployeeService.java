package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);

    Employee getEmployee(long employeeId);

    void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId);

    List<Employee> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO);

}
