package com.iktakademija.Projekat.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iktakademija.Projekat.entities.CategoryEntity;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {
	
	List<CategoryEntity> findAllByName(String name);
	CategoryEntity findByName(String name);
	
	// Find random record from table
	@Query(nativeQuery = true, value = "SELECT * FROM categories ORDER BY rand() LIMIT 1")
	CategoryEntity findRandom();
	
	List<CategoryEntity> findAll();
}
