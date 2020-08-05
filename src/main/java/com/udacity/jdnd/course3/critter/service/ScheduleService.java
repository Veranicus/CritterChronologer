package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Schedule;

import java.util.List;

public interface ScheduleService {

    Schedule createSchedule(Schedule schedule, List<Long> petIds,
                            List<Long> employeeIds);

    List<Schedule> getAllSchedules();

    List<Schedule> getScheduleForPet(Long petId);

    List<Schedule> getScheduleForEmployee(Long employeeId);

    List<Schedule> getScheduleForCustomer(Long customerId);

}
