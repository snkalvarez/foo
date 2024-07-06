package com.micro.repository;

import com.micro.model.Foo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FooRepository extends JpaRepository<Foo, Long> {

    Foo findByName(String name);
    //mas ejemplos de consutas personalizadas
    //Foo findByNameAndId(String name, Long id);
    //List<Foo> findByNameOrId(String name, Long id);
    //List<Foo> findByNameLike(String name);
    //List<Foo> findByNameNotLike(String name);
    //List<Foo> findByNameStartingWith(String name);
    //List<Foo> findByNameEndingWith(String name);
    //List<Foo> findByNameContaining(String name);
    //List<Foo> findByNameIsNull();
    //List<Foo> findByNameIsNotNull();
    //List<Foo> findByNameOrderByDesc(String name);
    //List<Foo> findByNameOrderByAsc(String name);
    //List<Foo> findByNameOrderByDesc();
    //List<Foo> findByNameOrderByAsc();
    //List<Foo> findByNameOrderByDescIdAsc(String name);
    //List<Foo> findByNameOrderByAscIdDesc(String name);
    //List<Foo> findByNameOrderByDescIdDesc(String name);


    @Query("SELECT f FROM Foo f WHERE LOWER(f.name) = LOWER(:name)")
    Foo retriveByName(String name);
}
