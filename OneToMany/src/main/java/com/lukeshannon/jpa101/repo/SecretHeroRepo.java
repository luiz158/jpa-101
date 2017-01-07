package com.lukeshannon.jpa101.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lukeshannon.jpa101.model.SuperHero;

public interface SecretHeroRepo extends CrudRepository<SuperHero, Long> {
	
	List<SuperHero> findByName(String name);

}
