package org.example.pet.controller;

import org.example.pet.exception.PetNotFoundException;
import org.example.pet.model.Pet;
import org.example.pet.service.PetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PetController.class)
class PetControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private PetService service;


    @Test
    void getPetById() throws Exception {
        Pet pet = new Pet();
        pet.setId(5L);
        when(service.findById(5L)).thenReturn(pet);

        mvc.perform(get("/pets/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));
        verify(service).findById(5L);
    }

    @Test
    void getPetByIdNotFound() throws Exception {
        when(service.findById(9L)).thenThrow(new PetNotFoundException("Pet not found"));

        mvc.perform(get("/pets/9"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Pet not found"));
        verify(service).findById(9L);
    }

    @Test
    void createPetSuccess() throws Exception {
        Pet pet = new Pet();
        pet.setId(10L);
        pet.setName("Buddy");
        pet.setSpecies("Dog");

        when(service.save(any(Pet.class))).thenReturn(pet);

        String json = "{\"name\":\"Buddy\",\"species\":\"Dog\"}";

        mvc.perform(post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("Buddy"));
        verify(service).save(any(Pet.class));
    }

    @Test
    void updatePetSuccess() throws Exception {
        Pet pet = new Pet();
        pet.setId(7L);
        pet.setName("Whiskers");
        pet.setSpecies("Cat");

        when(service.update(eq(7L), any(Pet.class))).thenReturn(pet);

        String json = "{\"name\":\"Whiskers\",\"species\":\"Cat\"}";

        mvc.perform(put("/pets/7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.name").value("Whiskers"));
        verify(service).update(eq(7L), any(Pet.class));
    }

    @Test
    void deletePetSuccess() throws Exception {
        doNothing().when(service).delete(3L);

        mvc.perform(delete("/pets/3"))
                .andExpect(status().isNoContent());

        verify(service).delete(3L);
    }

    @Test
    void deletePetNotFound() throws Exception {
        doThrow(new PetNotFoundException("Pet not found")).when(service).delete(5L);

        mvc.perform(delete("/pets/5"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Pet not found"));

        verify(service).delete(5L);
    }


    @Test
    void createPetValidationError() throws Exception {
        String invalidJson = "{\"species\":\"Dog\"}";

        mvc.perform(post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }
}
