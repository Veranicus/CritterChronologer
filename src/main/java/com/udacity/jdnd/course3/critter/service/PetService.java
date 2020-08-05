package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;

import java.util.List;

public interface PetService {

    Pet savePet(Pet pet, Long customerId);

    Pet getPet(long petId);

    List<Pet> getAllPets();

    List<Pet> getPetsByOwner(long ownerId);
}
