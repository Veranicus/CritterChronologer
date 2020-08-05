package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer getCustomer(Long customerId);

    Customer saveCustomer(Customer customer, List<Long> petIds);

    List<Customer> getAllCustomers();

    Customer getOwnerByPet(long petId);


}
