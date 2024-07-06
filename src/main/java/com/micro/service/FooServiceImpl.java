package com.micro.service;

import com.micro.exception.MyResourcenotFoundException;
import com.micro.model.Foo;
import com.micro.repository.FooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FooServiceImpl implements FooService{

    @Autowired
    private FooRepository fooRepository;

    @Override
    public List<Foo> findAll() {
        return fooRepository.findAll();
    }

    @Override
    public Foo findById(Long id) {
         return fooRepository.findById(id).orElseThrow(() -> new MyResourcenotFoundException("Foo not found"));
    }

    @Override
    public Long create(Foo foo) {
        Foo savedFoo = fooRepository.save(foo);
        return savedFoo.getId();
    }

    @Override
    public void update(Foo foo) {
        Foo savedFoo = fooRepository.findById(foo.getId()).get();
        savedFoo.setName(foo.getName());
        fooRepository.save(savedFoo);
    }

    @Override
    public void deleteById(Long id) {
        fooRepository.deleteById(id);
    }
}
