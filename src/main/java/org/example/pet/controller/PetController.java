package org.example.pet.controller;

import jakarta.validation.constraints.Min;
import org.example.pet.dto.PetRequestDTO;
import org.example.pet.dto.PetResponseDTO;
import org.example.pet.model.Pet;
import org.example.pet.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService service;

    @GetMapping("/{id}")
    public Pet get(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<PetResponseDTO> save(@Valid @RequestBody PetRequestDTO petRequestDTO) {
        Pet pe = getPet(petRequestDTO);
        Pet created = service.save(pe);
        return new ResponseEntity<>(PetResponseDTO.fromEntity(created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetResponseDTO> update(@PathVariable @Min(1) Long id, @Valid @RequestBody PetRequestDTO petRequestDTO) {
        Pet pet = getPet(petRequestDTO);
        Pet updated = service.update(id, pet);
        return ResponseEntity.ok(PetResponseDTO.fromEntity(updated));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(1) Long id) {
        service.delete(id);
    }

    private static Pet getPet(PetRequestDTO petRequestDTO) {
        Pet pe = new Pet();
        pe.setName(petRequestDTO.getName());
        pe.setSpecies(petRequestDTO.getSpecies());
        pe.setAge(petRequestDTO.getAge());
        pe.setOwnerName(petRequestDTO.getOwnerName());
        return pe;
    }
}