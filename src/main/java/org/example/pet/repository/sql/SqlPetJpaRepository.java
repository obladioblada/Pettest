package org.example.pet.repository.sql;

import org.example.pet.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SqlPetJpaRepository extends JpaRepository<Pet, Long> {}
