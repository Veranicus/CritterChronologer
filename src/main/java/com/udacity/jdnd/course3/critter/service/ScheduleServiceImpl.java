package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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
    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {

        Schedule schedule = new Schedule();

        BeanUtils.copyProperties(scheduleDTO, schedule);

        List<Pet> pets = new ArrayList<>();
        scheduleDTO.getPetIds().forEach(pet -> {
            pets.add(petRepository.findById(pet).get());
        });

        List<Employee> employees = new ArrayList<>();

        scheduleDTO.getEmployeeIds().forEach(employee -> {
            employees.add(employeeRepository.findById(employee).get());
        });

        schedule.setPets(pets);
        schedule.setEmployees(employees);


        scheduleDTO.setId(scheduleRepository.save(schedule).getId());
        return scheduleDTO;
    }

    @Override
    public List<ScheduleDTO> getAllSchedules() {

        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();


//        scheduleRepository.findAll().forEach();

        return null;
    }

    @Override
    public List<ScheduleDTO> getScheduleForPet(Long petId) {
        return null;
    }

    @Override
    public List<ScheduleDTO> getScheduleForEmployee(Long employeeId) {
        return null;
    }

    @Override
    public List<ScheduleDTO> getScheduleForCustomer(Long customerId) {
        return null;
    }
}
