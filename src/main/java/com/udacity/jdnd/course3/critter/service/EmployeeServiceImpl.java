package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        employee.setSchedule(null);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployee(long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(EntityNotFoundException::new);
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO) {

        List<Employee> allEmployees = employeeRepository.findAll();

        List<Employee> availableEmployeesDayOfWeek = new ArrayList<>();

        List<Employee> availableEmployeesTotal = new ArrayList<>();

        allEmployees.forEach(employee -> {
            employee.getDaysAvailable().forEach(dayOfWeek -> {
                if (dayOfWeek.equals(employeeRequestDTO.getDate().getDayOfWeek())) {
                    availableEmployeesDayOfWeek.add(employee);
                }
            });
        });

        availableEmployeesDayOfWeek.forEach(employee -> {
            if (employee.getSkills().containsAll(employeeRequestDTO.getSkills())) {
                availableEmployeesTotal.add(employee);
            }
        });

        return availableEmployeesTotal;
    }
}
