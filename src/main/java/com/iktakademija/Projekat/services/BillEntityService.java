package com.iktakademija.Projekat.services;

import java.time.LocalDate;
import java.util.List;

import com.iktakademija.Projekat.entities.BillEntity;
import com.iktakademija.Projekat.entities.OfferEntity;

public interface BillEntityService {
	
	// 2.4 u servisu zaduženom za rad sa računima, napisati metodu koja za prosleđene datume vraća 
	// račune koji se nalaze u datom periodu
	// pozvati je u okviru metode BillController-a za pronalazaksvihračunakojisukreiraniu 
	// odgovarajućem vremenskom periodu		
	public List<BillEntity> findBillBetweenDates(LocalDate startDate, LocalDate endDate);

	// 3.1 ne dozvoliti brisanje onih kategorija za koje postoje neistekle ponude i računi
	// napisati metodu u servisu zaduženom za rad sa ponudama koja proverava da li postoje 
	// ponude za datu kategoriju (kategoriju koja želi da se obriše)
	// napisati metodu u servisu zaduženom za rad sa računima koja proverava da li postoje 
	// aktivni računi za datu kategoriju
	// pozvati prethodno kreirane metode u metodi za brisanje kategorije u okviru CategoryController-a
	public Boolean isCategoryUnused(Integer id);	
	
	// T4-3.3 Ukoliko se ponuda proglasi isteklom potrebno je otkazati sve račune koji sadrže tu ponudu
	// u okviru servisa zaduženog za rad sa računima napisati metodu koja otkazuje sve račune odgovarajuće ponude
	// pozvati je u okviru metode za promenu statusa ponude u OfferController-u
//	public void cancelAllBillsForExpiredOffer(Integer id);
	public void cancelAllBillsForExpiredOffer(OfferEntity offer);
	
}
