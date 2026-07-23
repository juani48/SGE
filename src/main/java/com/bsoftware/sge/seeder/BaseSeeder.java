package com.bsoftware.sge.seeder;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseSeeder<T, TId> implements CommandLineRunner {

    private final CrudRepository<T, TId> repository;
    private List<T> data;
    private String name;

    public BaseSeeder(CrudRepository<T, TId> repository, List<T> data, String name) {
        this.repository = repository;
        this.data = data;
        this.name = name;

    }

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0) {
            System.out.println("Seeding " + name + " ...");
            repository.saveAll(data);
        }
        System.out.println(name + " seeded.");
    }
}