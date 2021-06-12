package com.iktakademija.Projekat.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iktakademija.Projekat.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

	List<UserEntity> findAllByUsername(String username);
	List<UserEntity> findAllByEmail(String email);
	List<UserEntity> findAll();
	Optional<UserEntity> findByUsername (String username);
	
	// Find random record in table
	@Query(nativeQuery = true, value = "SELECT * FROM users ORDER BY rand() LIMIT 1")
	UserEntity findRandom();	
	
}
