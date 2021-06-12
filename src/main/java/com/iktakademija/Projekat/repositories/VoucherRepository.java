package com.iktakademija.Projekat.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.iktakademija.Projekat.entities.OfferEntity;
import com.iktakademija.Projekat.entities.UserEntity;
import com.iktakademija.Projekat.entities.VoucherEntity;

public interface VoucherRepository extends CrudRepository<VoucherEntity, Integer> {

	List<VoucherEntity> findByUser(UserEntity buyer);
	List<VoucherEntity> findByOffer(OfferEntity offers);
	List<VoucherEntity> findByExpirationDateGreaterThanEqual(LocalDate now);
	List<VoucherEntity> findAll();
	
	@Modifying @Transactional
	@Query(value = "UPDATE vouchers SET user=null where user=:val", nativeQuery = true)
	void nullColumnUser(@Param("val") String value);
	
	@Modifying @Transactional
	@Query(value = "UPDATE vouchers SET offer=null where offer=:val", nativeQuery = true)
	void nullColumnOffer(@Param("val") String value);
}
