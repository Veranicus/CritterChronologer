package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s INNER JOIN Pet p " +
            "ON s.id = p.schedule.id WHERE p.id = :petId ")
    List<Schedule> findAllByPetsId(@Param("petId") Long petId);


    @Query("SELECT s FROM Schedule s INNER JOIN Employee e " +
            "ON s.id = e.schedule.id WHERE e.id = :employeeId ")
    List<Schedule> findAllByEmployeeId(@Param("employeeId") Long employeeId);

    @Query("SELECT s FROM Schedule s INNER JOIN Pet p " +
            "ON s.id = p.schedule.id WHERE p.customer.id = :customerId ")
    List<Schedule> findAllByCustomerId(@Param("customerId") Long customerId);
}
