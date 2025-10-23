package org.example.pet.service;

import org.example.pet.exception.PetNotFoundException;
import org.example.pet.model.Pet;

/**
 * Service interface for managing Pet entities.
 */
public interface PetService {

    /**
     * Save a new Pet entity in the repository.
     *
     * @param pet the Pet to create, must not be null
     * @return the created Pet with generated ID
     */
    Pet save(Pet pet);

    /**
     * Updates an existing Pet entity identified by the given ID.
     *
     * @param id  the ID of the Pet to update
     * @param pet the Pet data to update, must not be null
     * @return the updated Pet entity
     */
    Pet update(Long id, Pet pet) throws PetNotFoundException;;

    /**
     * Deletes the Pet entity identified by the given ID.
     *
     * @param id the ID of the Pet to delete
     */
    void delete(Long id);

    /**
     * Finds a Pet entity by its ID.
     *
     * @param id the ID of the Pet to find
     * @return the found Pet entity
     * @throws RuntimeException if no Pet is found for the given ID
     */
    Pet findById(Long id) throws PetNotFoundException;;
}