package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {

        Schedule schedule = new Schedule();

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

        schedule.setDate(scheduleDTO.getDate());
        schedule.setActivities(scheduleDTO.getActivities());

        scheduleDTO.setId(scheduleRepository.save(schedule).getId());
        return scheduleDTO;
    }

    @Override
    public List<ScheduleDTO> getAllSchedules() {

        return convertListSchedulesToListSchedulesDtos(scheduleRepository.findAll());
    }

    @Override
    public List<ScheduleDTO> getScheduleForPet(Long petId) {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<Schedule> schedulesPets = new ArrayList<>();
        schedules.forEach(schedule -> {
            schedule.getPets().forEach(pet -> {
                if (pet.getId().equals(petId)) {
                    schedulesPets.add(schedule);
                }
            });
        });
        return convertListSchedulesToListSchedulesDtos(schedulesPets);
    }

    @Override
    public List<ScheduleDTO> getScheduleForEmployee(Long employeeId) {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<Schedule> scheduleEmployees = new ArrayList<>();
        schedules.forEach(schedule -> {
            schedule.getEmployees().forEach(employee -> {
                if (employee.getId().equals(employeeId)) {
                    scheduleEmployees.add(schedule);
                }
            });
        });
        return convertListSchedulesToListSchedulesDtos(scheduleEmployees);
    }

    @Override
    public List<ScheduleDTO> getScheduleForCustomer(Long customerId) {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<Schedule> scheduleCustomers = new ArrayList<>();
        schedules.forEach(schedule -> {
            schedule.getPets().forEach(pet -> {
                if (pet.getCustomer().getId().equals(customerId)) {
                    scheduleCustomers.add(schedule);
                }
            });
        });


        return convertListSchedulesToListSchedulesDtos(scheduleCustomers);
    }

    private List<ScheduleDTO> convertListSchedulesToListSchedulesDtos(List<Schedule> schedules) {

        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        schedules.forEach(schedule -> {

            List<Long> employeeIds = new ArrayList<>();

            List<Long> petIds = new ArrayList<>();
            schedule.getEmployees().forEach(employee -> {
                employeeIds.add(employee.getId());
            });
            schedule.getPets().forEach(pet -> {
                petIds.add(pet.getId());
            });
            scheduleDTOS.add(new ScheduleDTO(schedule.getId(), employeeIds, petIds,
                    schedule.getDate(), schedule.getActivities()));
        });
        return scheduleDTOS;

    }
}
