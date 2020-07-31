package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public PetDTO savePet(PetDTO petDTO) {
            System.out.println(petDTO.getCustomerId());
            Pet pet = new Pet();
            BeanUtils.copyProperties(petDTO, pet);
        if (petDTO.getCustomerId() != null) {
            pet.setCustomer(customerService.getCustomer(petDTO.getCustomerId()));
//            customerService.updateCustomerPets(petDTO.getCustomerId(), savedPet);
            petDTO.setId(petRepository.save(pet).getId());
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
        petDTO.setCustomerId(pet.getCustomer().getId());
        return petDTO;
    }

    @Override
    public List<PetDTO> getAllPets() {
        List<PetDTO> petDTOS = new ArrayList<>();
        petRepository.findAll().forEach(
                pet -> {
                    PetDTO petDTO = new PetDTO();
                    BeanUtils.copyProperties(pet, petDTO);
                    petDTO.setCustomerId(pet.getCustomer().getId());
                    petDTOS.add(petDTO);
                }
        );
        return petDTOS;
    }

    @Override
    public List<PetDTO> getPetsByOwner(long ownerId) {
        List<PetDTO> returnedPetsOfOwner = new ArrayList<>();
        List<Pet> returnedPets = petRepository.findAllByCustomer_Id(ownerId);
        if (returnedPets != null) {
            for (int i = 0; i < returnedPets.size(); i++) {
                PetDTO petDTO = new PetDTO();
                BeanUtils.copyProperties(returnedPets.get(i),petDTO);
                petDTO.setCustomerId(returnedPets.get(i).getCustomer().getId());
                returnedPetsOfOwner.add(petDTO);
            }
        }
        return returnedPetsOfOwner;
    }
}
