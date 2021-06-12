package com.iktakademija.Projekat.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktakademija.Projekat.entities.BillEntity;
import com.iktakademija.Projekat.entities.OfferEntity;
import com.iktakademija.Projekat.repositories.BillRepository;

@Service
public class BillEntityServiceImpl implements BillEntityService {
	
	@Autowired
	BillRepository billRepository;
	
	// 2.4 u servisu zaduženom za rad sa računima, napisati metodu koja za
	// prosleđene datume vraća račune koji se nalaze u datom periodu
	// pozvati je u okviru metode BillController-a za pronalazaksvihračunakojisukreiraniu
	// odgovarajućem vremenskom periodu
	@Override
	public List<BillEntity> findBillBetweenDates(LocalDate startDate, LocalDate endDate) {

		return billRepository.findByBillCreatedBetweenOrderByBillCreatedAsc(startDate, endDate);
	}
	// TODO preporucio a ipak vrati u kontroler posto nije problem da ide automatski
	
	// 3.1 ne dozvoliti brisanje onih kategorija za koje postoje neistekle ponude i računi
	// napisati metodu u servisu zaduženom za rad sa ponudama koja proverava da li postoje 
	// ponude za datu kategoriju (kategoriju koja želi da se obriše)
	// napisati metodu u servisu zaduženom za rad sa računima koja proverava da li postoje 
	// aktivni računi za datu kategoriju
	// pozvati prethodno kreirane metode u metodi za brisanje kategorije u okviru CategoryController-a
	@Override
	public Boolean isCategoryUnused(Integer id)
	{
		if (id == null) return null;
		return billRepository.findAllBillsByCategoryId(id).isEmpty();
	}	
	
	// T4-3.3 Ukoliko se ponuda proglasi isteklom potrebno je otkazati sve račune koji sadrže tu ponudu
	// u okviru servisa zaduženog za rad sa računima napisati metodu koja otkazuje sve račune odgovarajuće ponude
	// pozvati je u okviru metode za promenu statusa ponude u OfferController-u
//	@Override
//	public void cancelAllBillsForExpiredOffer(Integer offerId) {
//		
//		// Trivial check
//		if (offerId == null) return;
//		
//		// Get all bill that contains offer
//		List<BillEntity> bills = billRepository.findAllByofferBillEntity(offerId);
//		for (BillEntity billEntity : bills) {
//			billEntity.setPaymentCanceled(true);				
//		}
//	}
	
	@Override
	public void cancelAllBillsForExpiredOffer(OfferEntity offer){
		
		// Trivial check
		if (offer == null) return;
		
		// Get all bill that contains offer
		List<BillEntity> bills = billRepository.findAllByofferBillEntity(offer);
		for (BillEntity billEntity : bills) {
			billEntity.setPaymentCanceled(true);				
		}
	}
}
