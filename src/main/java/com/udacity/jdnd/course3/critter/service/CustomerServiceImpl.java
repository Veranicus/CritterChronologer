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
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        List<Pet> petsToAssociateWithCustomer = new ArrayList<>();
        if (customerDTO.getPetIds() != null && customerDTO.getPetIds().size() > 0) {
            customerDTO.getPetIds().forEach(petId -> {
                Optional<Pet> retrievedPet = petRepository.findById(petId);
                if (retrievedPet.isPresent() && retrievedPet.get().getCustomer() == null) {
                    retrievedPet.get().setCustomer(customer);
                    petsToAssociateWithCustomer.add(retrievedPet.get());
                }
            });
        }
        customer.setPets(petsToAssociateWithCustomer);
        customerDTO.setId(customerRepository.save(customer).getId());
        return customerDTO;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        List<Customer> customers = customerRepository.findAll();
        for (int i = 0; i < customers.size(); i++) {
            List<Long> petIds = new ArrayList<>();
            CustomerDTO customerDTO = new CustomerDTO();
            BeanUtils.copyProperties(customers.get(i), customerDTO);
            if (customers.get(i).getPets() != null) {
                customers.get(i).getPets().forEach(pet -> {
                    petIds.add(pet.getId());
                });
            }
            customerDTO.setPetIds(petIds);
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    @Override
    public CustomerDTO getOwnerByPet(long petId) {
        CustomerDTO customerDTO = new CustomerDTO();
        Customer customer = petRepository.findById(petId)
                .orElseThrow(EntityNotFoundException::new).getCustomer();
        BeanUtils.copyProperties(customer, customerDTO);
        List<Long> petIds = new ArrayList<>();
        if (customer.getPets() != null) {
            customer.getPets().forEach(pet -> {
                petIds.add(pet.getId());
            });
        }
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

}
