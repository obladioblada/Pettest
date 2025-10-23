package org.example.pet.repository;

import org.example.pet.model.Pet;

import java.util.Optional;

public interface PetRepository {
    Pet save(Pet pet);
    Optional<Pet> findById(Long id);
    void deleteById(Long id);
}