package org.example.pet.service;

import org.example.pet.exception.PetNotFoundException;
import org.example.pet.model.Pet;
import org.example.pet.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetServiceImplTest {

    private PetRepository repository;
    private PetServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(PetRepository.class);
        service = new PetServiceImpl(repository);
    }

    @Test
    void createShouldDelegateToRepository() {
        Pet pet = Mockito.mock(Pet.class);
        when(repository.save(pet)).thenReturn(pet);
        Pet result = service.save(pet);
        assertSame(pet, result);
        verify(repository).save(pet);
    }

    @Test
    void petIdShouldBeIncremental() {
        Pet pet = Mockito.mock(Pet.class);
        when(pet.getId()).thenReturn(1L);
        Pet pet2 = Mockito.mock(Pet.class);
        when(pet2.getId()).thenReturn(2L);

        when(repository.save(pet)).thenReturn(pet);
        when(repository.save(pet2)).thenReturn(pet2);
        Pet result = service.save(pet);
        Pet result2 = service.save(pet2);

        assertSame(pet, result);
        assertSame(pet2, result2);
        assertTrue(result2.getId() > result.getId());
        verify(repository).save(pet);
    }

    @Test
    void findByIdWhenPresent() {
        Pet pet = Mockito.mock(Pet.class);
        when(repository.findById(pet.getId())).thenReturn(Optional.of(pet));
        Pet result = service.findById(pet.getId());
        assertEquals(pet.getId(), result.getId());
        verify(repository).findById(pet.getId());
    }

    @Test
    void findByIdWhenMissingThrows() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(PetNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    void updateShouldUseSave() {
        Pet pet = new Pet();
        pet.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(pet));
        when(repository.save(pet)).thenReturn(pet);

        Pet updated = service.update(1L, pet);

        assertEquals(1L, updated.getId());
        verify(repository).save(pet);
    }

    @Test
    void testDeleteSuccess() {
        Pet pet = new Pet();
        pet.setId(1L);
        when(repository.findById(pet.getId())).thenReturn(Optional.of(pet));
        doNothing().when(repository).deleteById(pet.getId());

        service.delete(pet.getId());

        verify(repository).findById(pet.getId());
        verify(repository).deleteById(pet.getId());
    }

    @Test
    void testDeleteNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        PetNotFoundException thrown = assertThrows(PetNotFoundException.class, () -> {
            service.delete(1L);
        });

        assertEquals("Pet not found", thrown.getMessage());
        verify(repository).findById(1L);
        verify(repository, never()).deleteById(anyLong());
    }

}
