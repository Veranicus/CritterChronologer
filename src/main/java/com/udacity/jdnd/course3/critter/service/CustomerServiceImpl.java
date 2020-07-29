package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    private PetRepository petRepository;


    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, PetRepository petRepository) {
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    @Override
    public Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        customerDTO.setId(customerRepository.save(customer).getId());
        return customerDTO;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        customerRepository.findAll().forEach(
                customer -> {
                    CustomerDTO customerDTO = new CustomerDTO();
                    BeanUtils.copyProperties(customer, customerDTO);
                    customerDTOS.add(customerDTO);

                });
        return customerDTOS;
    }

    @Override
    public void updateCustomerPets(Long customerId, Pet pet) {
       Customer customer =  getCustomer(customerId);
       customer.getPets().add(pet);
       customerRepository.save(customer);
    }

    @Override
    public CustomerDTO getOwnerByPet(long petId) {
        CustomerDTO customerDTO = new CustomerDTO();
        Customer customer = petRepository.findById(petId)
                .orElseThrow(EntityNotFoundException::new).getCustomer();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }

}