package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final CustomerService customerService;


    @Autowired
    public PetServiceImpl(PetRepository petRepository, CustomerService customerService) {
        this.petRepository = petRepository;
        this.customerService = customerService;
    }

    @Override
    public Pet savePet(Pet pet, Long customerId) {
        if (customerId != null) {
            pet.setCustomer(customerService.getCustomer(customerId));
        }
        Pet pet1 = petRepository.save(pet);
        customerService.getCustomer(customerId).getPets().add(pet1);
        return pet1;
    }

    @Override
    public Pet getPet(long petId) {
        return petRepository.findById(petId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public List<Pet> getPetsByOwner(long ownerId) {
        return petRepository.findAllByCustomer_Id(ownerId);
    }
}
