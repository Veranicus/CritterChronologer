package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
    public Customer saveCustomer(Customer customer, List<Long> petIds) {
        List<Pet> petsToAssociateWithCustomer = new ArrayList<>();
        if (petIds != null && petIds.size() > 0) {
            petIds.forEach(petId -> {
                Optional<Pet> retrievedPet = petRepository.findById(petId);
                if (retrievedPet.isPresent() && retrievedPet.get().getCustomer() == null) {
                    retrievedPet.get().setCustomer(customer);
                    petsToAssociateWithCustomer.add(retrievedPet.get());
                }
            });
        }
        customer.setPets(petsToAssociateWithCustomer);
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getOwnerByPet(long petId) {
        return petRepository.findById(petId)
                .orElseThrow(EntityNotFoundException::new).getCustomer();
    }

}
