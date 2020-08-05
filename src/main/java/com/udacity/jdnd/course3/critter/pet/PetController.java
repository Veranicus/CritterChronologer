package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = new Pet();
//        pet.setBirthDate(petDTO.getBirthDate());
//        pet.setName(petDTO.getName());
//        pet.setNotes(petDTO.getNotes());
        BeanUtils.copyProperties(petDTO, pet);
        System.out.println(pet);
        return convertPetToPetDTO(petService.savePet(pet, petDTO.getCustomerId()));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertPetToPetDTO(petService.getPet(petId));
    }

    @GetMapping
    public List<PetDTO> getPets() {
        return convertPetsToPetsDto(petService.getAllPets());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return convertPetsToPetsDto(petService.getPetsByOwner(ownerId));
    }

    private List<PetDTO> convertPetsToPetsDto(List<Pet> pets) {
        List<PetDTO> petDTOS = new ArrayList<>();
        if (pets != null && !pets.isEmpty()) {
            pets.forEach(
                    pet -> {
                        PetDTO petDTO = new PetDTO();
                        BeanUtils.copyProperties(pet, petDTO);
                        if (pet.getCustomer() != null) {
                            petDTO.setCustomerId(pet.getCustomer().getId());
                        }
                        petDTOS.add(petDTO);
                    }
            );
        }
        return petDTOS;
    }

    private PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet,
                petDTO);
        if (pet.getCustomer() != null) {
            petDTO.setCustomerId(pet.getCustomer().getId());
        }
        return petDTO;
    }

}
