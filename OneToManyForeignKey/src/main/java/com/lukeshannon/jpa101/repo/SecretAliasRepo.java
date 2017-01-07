package com.lukeshannon.jpa101.repo;

import org.springframework.data.repository.CrudRepository;

import com.lukeshannon.jpa101.model.SuperAlias;

public interface SecretAliasRepo extends CrudRepository<SuperAlias, Long> {
	

}
