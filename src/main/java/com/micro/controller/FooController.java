package com.micro.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.exception.MyResourcenotFoundException;
import com.micro.model.Foo;
import com.micro.service.FooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foos")
public class FooController {

    @Autowired
    private FooService fooService;

    @GetMapping
    public List<Foo> getFoos() {
        return fooService.findAll();
    }

    @GetMapping("/{id}")
    @ExceptionHandler(MyResourcenotFoundException.class)
    public Foo getFoo(@PathVariable long id) {
        return fooService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createFoo(@RequestBody Foo foo) {
        return fooService.create(foo);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateFoo(@PathVariable Long id, @RequestBody Foo foo){
        fooService.update(foo);
    }

    @DeleteMapping("/{id}")
    public void deleteFoo(@PathVariable Long id){
        fooService.deleteById(id);
    }


}
