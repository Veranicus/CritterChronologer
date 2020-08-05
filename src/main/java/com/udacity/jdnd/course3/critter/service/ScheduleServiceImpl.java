package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private ScheduleRepository scheduleRepository;

    private PetRepository petRepository;

    private EmployeeRepository employeeRepository;


    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, PetRepository petRepository, EmployeeRepository employeeRepository) {
        this.scheduleRepository = scheduleRepository;
        this.petRepository = petRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Schedule createSchedule(Schedule schedule, List<Long> petIds,
                                   List<Long> employeeIds) {

        List<Pet> pets = new ArrayList<>();
        petIds.forEach(pet -> {
            pets.add(petRepository.findById(pet).orElseThrow(EntityNotFoundException::new));
        });

        List<Employee> employees = new ArrayList<>();

        employeeIds.forEach(employee -> {
            employees.add(employeeRepository.findById(employee).orElseThrow(EntityNotFoundException::new));
        });

        schedule.setPets(pets);
        schedule.setEmployees(employees);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        pets.forEach(pet -> {
            petRepository.findById(pet.getId()).orElseThrow(EntityNotFoundException::new).setSchedule(schedule);
//            pet.setSchedule(savedSchedule);
        });
        employees.forEach(employee -> {
//            employee.setSchedule(savedSchedule);
            employeeRepository.findById(employee.getId()).orElseThrow(EntityNotFoundException::new).setSchedule(schedule);
        });

        return savedSchedule;
    }

    @Override
    public List<Schedule> getAllSchedules() {

        return scheduleRepository.findAll();
    }

    @Override
    public List<Schedule> getScheduleForPet(Long petId) {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<Schedule> schedulesPets = new ArrayList<>();
        schedules.forEach(schedule -> {
            schedule.getPets().forEach(pet -> {
                if (pet.getId().equals(petId)) {
                    schedulesPets.add(schedule);
                }
            });
        });
        return schedulesPets;
    }

    @Override
    public List<Schedule> getScheduleForEmployee(Long employeeId) {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<Schedule> scheduleEmployees = new ArrayList<>();
        schedules.forEach(schedule -> {
            schedule.getEmployees().forEach(employee -> {
                if (employee.getId().equals(employeeId)) {
                    scheduleEmployees.add(schedule);
                }
            });
        });
        return scheduleEmployees;
    }

    @Override
    public List<Schedule> getScheduleForCustomer(Long customerId) {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<Schedule> scheduleCustomers = new ArrayList<>();
        schedules.forEach(schedule -> {
            schedule.getPets().forEach(pet -> {
                if (pet.getCustomer().getId().equals(customerId)) {
                    scheduleCustomers.add(schedule);
                }
            });
        });


        return scheduleCustomers;
    }
}
