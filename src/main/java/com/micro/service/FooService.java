package com.micro.service;

import com.micro.model.Foo;

import java.util.List;

public interface FooService {

    List<Foo> findAll();
    Foo findById(Long id);
    Long create(Foo foo);
    void update(Foo foo);
    void deleteById(Long id);
}
