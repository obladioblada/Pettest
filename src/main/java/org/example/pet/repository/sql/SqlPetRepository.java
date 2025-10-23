package org.example.pet.repository.sql;

import org.example.pet.model.Pet;
import org.example.pet.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile({"local", "sql"})
public class SqlPetRepository implements PetRepository {

    private final SqlPetJpaRepository jpaRepository;

    @Autowired
    public SqlPetRepository(SqlPetJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Pet save(Pet pet) {
        return jpaRepository.save(pet);
    }

    @Override
    public Optional<Pet> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
