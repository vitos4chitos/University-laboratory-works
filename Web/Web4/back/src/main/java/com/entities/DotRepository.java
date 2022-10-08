package com.entities;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DotRepository extends CrudRepository<Dot, Integer> {
    void deleteDotsByLogin(String login);
    List<Dot> getAllByLogin(String login);
}
