package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();

        BeanUtils.copyProperties(scheduleDTO, schedule);

        return convertScheduleToScheduleDTO(scheduleService.createSchedule(schedule,
                scheduleDTO.getPetIds(), scheduleDTO.getEmployeeIds()));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return convertListSchedulesToListSchedulesDtos(scheduleService.getAllSchedules());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return convertListSchedulesToListSchedulesDtos(scheduleService.getScheduleForPet(petId));
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return convertListSchedulesToListSchedulesDtos(scheduleService.getScheduleForEmployee(employeeId));
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return convertListSchedulesToListSchedulesDtos(scheduleService.getScheduleForCustomer(customerId));
    }


    private List<ScheduleDTO> convertListSchedulesToListSchedulesDtos(List<Schedule> schedules) {

        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        schedules.forEach(schedule -> {
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        });
        return scheduleDTOS;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO;

        List<Long> employeeIds = new ArrayList<>();

        List<Long> petIds = new ArrayList<>();

        schedule.getEmployees().forEach(employee -> {
            employeeIds.add(employee.getId());
        });
        schedule.getPets().forEach(pet -> {
            petIds.add(pet.getId());
        });
        scheduleDTO = new ScheduleDTO(schedule.getId(), employeeIds, petIds,
                schedule.getDate(), schedule.getActivities());

        return scheduleDTO;

    }
}
