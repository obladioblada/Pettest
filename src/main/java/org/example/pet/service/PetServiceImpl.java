package org.example.pet.service;

import org.example.pet.exception.PetNotFoundException;
import org.example.pet.model.Pet;
import org.example.pet.repository.PetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PetServiceImpl implements PetService {

    private static final Logger logger = LoggerFactory.getLogger(PetServiceImpl.class);

    private final PetRepository repository;

    public PetServiceImpl(PetRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pet save(Pet pet) {
        logger.info("Creating new pet: {}", pet.getName());
        return repository.save(pet);
    }

    @Override
    public Pet update(Long id, Pet pet) {
        checkPetExists(id);
        logger.info("Updating pet with id {}", id);
        pet.setId(id);
        return repository.save(pet);
    }

    @Override
    public void delete(Long id) {
        checkPetExists(id);
        logger.warn("Deleting pet with id {}", id);
        repository.deleteById(id);
    }

    @Override
    public Pet findById(Long id) {
        logger.info("Finding pet with id {}", id);
        return repository.findById(id).orElseThrow(() -> {;
            return new PetNotFoundException("Pet not found");
        });
    }

    private void checkPetExists(Long id) {
        if (repository.findById(id).isEmpty()) {
            logger.error("Pet with id {} not found", id);
            throw new PetNotFoundException("Pet not found");
        }
    }

}