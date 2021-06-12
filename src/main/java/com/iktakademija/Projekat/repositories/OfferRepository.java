package com.iktakademija.Projekat.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.iktakademija.Projekat.entities.OfferEntity;

public interface OfferRepository extends CrudRepository<OfferEntity, Integer> {

	List<OfferEntity> findAllByActionPriceBetween(Double A, Double B);
	List<OfferEntity> findAllByOfferName(String offerName);	
	List<OfferEntity> findAll();	
	
	// Find random record from table
	@Query(nativeQuery = true, value = "SELECT * FROM offers ORDER BY rand() LIMIT 1")
	OfferEntity findRandom();	
	
	List<OfferEntity> findByRegularPriceBetweenOrderByRegularPriceAsc(Double price1, Double price2);	
   //  Optional<OfferEntity> findByCategory(Optional<CategoryEntity> category);
	
	@Modifying @Transactional
	@Query(value = "UPDATE offers SET cateoffers=null where cateoffers=:val", nativeQuery = true)
	void nullColumnCateoffers(@Param("val") String value);
	
	@Modifying @Transactional
	@Query(value = "UPDATE offers SET useroffers=null where useroffers=:val", nativeQuery = true)
	void nullColumnUseroffers(@Param("val") String value);
	
	
	List<OfferEntity> findAllOffersByCategoryId(Integer id);
}