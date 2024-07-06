package com.micro.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FooControllerAppIntegrationTest {

    @Autowired
    private MockMvc mockMvc;//para simular peticiones HTTP tambien podria usar una alternativa como TestRestTemplate

    @Test
    @DirtiesContext//para garantizar que cada prueba se hace en un contexto limpio
    public void whenGetFooById_thenStatus200() throws Exception {
        String fooJson = "{\"name\": \"Test Foo\"}";

        mockMvc.perform(post("/foos")//crear un item
                        .contentType(MediaType.APPLICATION_JSON)//tipo de contenido
                        .content(fooJson))//contenido
                .andExpect(status().isCreated());//andExpect es un método de la clase ResultActions que nos permite realizar aserciones sobre la respuesta
        //status() es un método de la clase ResultActions que nos permite acceder al estado de la respuesta

        mockMvc.perform(get("/foos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))//content() es un método de la clase ResultActions que nos permite acceder al contenido de la respuesta
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Test Foo"));
    }

    //delete item
    @Test
    @DirtiesContext
    public void whenDeleteFooById_thenStatus200() throws Exception {

        //primero crearlo
        String fooJson = "{\"name\": \"Test Foo\"}";
        //otra manera
        //Foo foo = new Foo();
        //foo.setName("Test Foo");
        //String fooJson = new ObjectMapper().writeValueAsString(foo);

        mockMvc.perform(post("/foos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fooJson))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/foos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/foos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DirtiesContext
    public void whenGetFoos_thenStatus200() throws Exception {
        mockMvc.perform(get("/foos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    @DirtiesContext
    public void whenCreateFoo_thenStatus201() throws Exception {
        String fooJson = "{\"name\": \"Test Foo\"}";

        mockMvc.perform(post("/foos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fooJson))
                .andExpect(status().isCreated());
    }



    @Test
    @DirtiesContext
    public void whenUpdateFoo_thenStatus200() throws Exception {
        String fooJson = "{\"name\": \"Test Foo\"}";

        mockMvc.perform(post("/foos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fooJson))
                .andExpect(status().isCreated());

        String updatedFooJson = "{\"id\": 1, \"name\": \"Test Foo Updated\"}";

        mockMvc.perform(put("/foos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedFooJson))
                .andExpect(status().isOk());

        mockMvc.perform(get("/foos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Test Foo Updated"));
    }

}