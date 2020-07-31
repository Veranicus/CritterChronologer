package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomerService {

    Customer getCustomer(Long customerId);

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    @Transactional
    void updateCustomerPets(Long customerId,Pet pet);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getOwnerByPet(long petId);


}
