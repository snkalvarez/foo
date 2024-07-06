package com.micro.controller;

import com.micro.model.Foo;
import com.micro.service.FooService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;


import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FooController.class)
public class FooControllerWebLayerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FooService fooService;

    @Test
    public void whenGetFoos_thenStatus200() throws Exception {
        Foo foo = new Foo();
        foo.setId(1L);
        foo.setName("Test Foo");

        when(fooService.findAll()).thenReturn(Collections.singletonList(foo));

        mockMvc.perform(get("/foos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(foo.getName())));
    }

    @Test
    public void whenCreateFoo_thenStatus201() throws Exception {
        Foo foo = new Foo();
        foo.setId(1L);
        foo.setName("Test Foo");

        when(fooService.create(foo)).thenReturn(foo.getId());

        String fooJson = "{\"name\": \"Test foo\"}";

        mockMvc.perform(post("/foos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(fooJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenGetFooById_thenStatus200() throws Exception {
        Foo foo = new Foo();
        foo.setId(1L);
        foo.setName("Test Foo");

        when(fooService.findById(anyLong())).thenReturn(foo);

        mockMvc.perform(get("/foos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(foo.getId().intValue())))
                .andExpect(jsonPath("$.name", is(foo.getName())));
    }

    @Test
    public void whenUpdateFoo_thenStatus200() throws  Exception{
        Foo foo = new Foo();
        foo.setId(1L);
        foo.setName("Test Foo");

        when(fooService.findById(anyLong())).thenReturn(foo);

        Foo updatedFoo = new Foo();
        updatedFoo.setId(1L);
        updatedFoo.setName("Test Foo Updated");

        String updatedFooJson = "{ \"id\": 1, \"name\": \"Test Foo Updated\"}";

        mockMvc.perform(put("/foos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedFooJson))
                .andExpect(status().isOk());

        verify(fooService, times(1)).update(updatedFoo);
    }

    @Test
    public void whenDeleteFoo_thenStatus200() throws Exception {
        doNothing().when(fooService).deleteById(anyLong());

        mockMvc.perform(delete("/foos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(fooService, times(1)).deleteById(1L);
    }
}
