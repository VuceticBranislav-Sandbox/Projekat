package com.iktakademija.Projekat.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.iktakademija.Projekat.entities.BillEntity;
import com.iktakademija.Projekat.entities.OfferEntity;

public interface BillRepository extends CrudRepository<BillEntity, Integer> {

	@Query(value = "SELECT * FROM bills WHERE user=:id", nativeQuery = true)
	List<BillEntity> findAllbillsUserEntityById(@Param("id") Integer id);
	
	@Query(value = "SELECT * FROM bills AS a INNER JOIN offer_entity AS b ON a.bills_offers"
			+ " = b.id WHERE cate_offers = ?1", nativeQuery = true  )
	List<BillEntity> findAllBillsByCategory(Integer id);	
	// TODO proveriti nativeQuery
	
	@Query("SELECT a FROM bills as a WHERE bill_created BETWEEN ?1 AND ?2")	
	List<BillEntity> findAllbillsInDateRange(LocalDate from, LocalDate to);
	// TODO proveriti nativeQuery
	
	@Modifying @Transactional
	@Query(value = "UPDATE bills SET user=null where user=:val", nativeQuery = true)
	void nullColumnUser(@Param("val") String value);
	
	@Modifying @Transactional
	@Query(value = "UPDATE bills SET offer=null where offer=:val", nativeQuery = true)
	void nullColumnOffer(@Param("val") String value);
	
	List<BillEntity> findAll();

	List<BillEntity> findByBillCreatedBetweenOrderByBillCreatedAsc(LocalDate startDate, LocalDate endDate);

	@Query(value = "SELECT * FROM bills as a INNER JOIN offers as b ON a.offer = b.id WHERE cateoffers=:id", nativeQuery = true)
	List<BillEntity> findByCategory(Integer id);

	List<BillEntity> findAllById(Integer id);
	
	//@Query("select b FROM bills as b join offers as c join categories as d where d.id = :id")
	@Query("FROM bills as b where b.offerBillEntity.category.id = :id")
	List<BillEntity> findAllBillsByCategoryId(@Param("id") Integer id);
	        
	// potrebno je otkazati sve račune koji sadrže tu ponudu u okviru servisa zaduženog za rad sa računima napisati metodu koja otkazuje sve račune odgovarajuće ponude
//	@Query("FROM bills as b where b.offerBillEntity.id = :id")
//	List<BillEntity> findAllByofferBillEntity(@Param("id") Integer id);
	List<BillEntity> findAllByofferBillEntity(@Param("id") OfferEntity id);
	
	// depracticed	
//	Optional<BillEntity> findByUser(Optional<UserEntity> buyer);
//	// @Query("SELECT * FROM BillEntity WHERE offer IN (SELECT id FROM OfferEntity WHERE category = :categoryId")
//	Optional <BillEntity> findByOffer(Optional<OfferEntity> offers);	
//	Optional <BillEntity> findByBillCreatedBetweenOrderByBillCreatedAsc(LocalDate startDate, LocalDate endDate);
}
