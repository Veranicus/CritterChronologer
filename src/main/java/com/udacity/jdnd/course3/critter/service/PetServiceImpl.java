package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final CustomerService customerService;


    @Autowired
    public PetServiceImpl(PetRepository petRepository, CustomerService customerService) {
        this.petRepository = petRepository;
        this.customerService = customerService;
    }

    @Override
    public PetDTO savePet(PetDTO petDTO) {
        if (petDTO.getCustomerId() != null) {
            System.out.println(petDTO.getCustomerId());
            Pet pet = new Pet();
            BeanUtils.copyProperties(petDTO, pet);
            pet.setCustomer(customerService.getCustomer(petDTO.getCustomerId()));
            customerService.updateCustomerPets(petDTO.getCustomerId(), petRepository.save(pet));
        }
        return petDTO;
    }

    @Override
    public PetDTO getPet(long petId) {
        PetDTO petDTO = new PetDTO();
        Pet pet = petRepository.findById(petId)
                .orElseThrow(EntityNotFoundException::new);
        BeanUtils.copyProperties(pet,
                petDTO);
        return petDTO;
    }

    @Override
    public List<PetDTO> getAllPets() {
        List<PetDTO> petDTOS = new ArrayList<>();
        petRepository.findAll().forEach(
                pet -> {
                    PetDTO petDTO = new PetDTO();
                    BeanUtils.copyProperties(pet, petDTO);
                    petDTOS.add(petDTO);
                }
        );
        return petDTOS;
    }

    @Override
    public List<PetDTO> getPetsByOwner(long ownerId) {
        return null;
    }
}
