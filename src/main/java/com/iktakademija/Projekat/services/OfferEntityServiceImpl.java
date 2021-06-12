package com.iktakademija.Projekat.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktakademija.Projekat.entities.OfferEntity;
import com.iktakademija.Projekat.repositories.OfferRepository;

@Service
public class OfferEntityServiceImpl implements OfferEntityService {

	@Autowired
	OfferRepository offerRepository;
	
	@Override
	// 2.1 u servisu zaduženom za rad sa ponudama, napisati metodu koja za prosleđen ID ponude, 
	// vrši izmenu broja kupljenih/dostupnih ponuda
	public Boolean updateAvailableOffers(Integer id, Integer buyAmount) {
		
		// Trivial check
		if (id == null || buyAmount == null || buyAmount < 0) return false;
		
		// Find by offer id	
		Optional<OfferEntity> op = offerRepository.findById(id);   
		if (op.isEmpty()) return false;
		OfferEntity offer = op.get();
		
		// Check availible amount
		if (offer.getAvailableOffers() < buyAmount.intValue()) return false;
		
		// Change amount. Keep 0 as minimum.
		offer.setBoughtOffers(Math.max(0, offer.getBoughtOffers() + buyAmount.intValue()));						
		offer.setAvailableOffers(Math.max(0, offer.getAvailableOffers() - buyAmount.intValue()));

		// Change made
		offerRepository.save(offer);
		return true;
	}
	
	public Boolean isCategoryUnused(Integer id)
	{
		if (id == null) return null;		
		return offerRepository.findAllOffersByCategoryId(id).isEmpty();
	}
	
// 2.1 u servisu zaduženom za rad sa ponudama, napisati metodu koja za prosleđen ID ponude, 
// vrši izmenu broja kupljenih/dostupnih ponuda
	
// 2.2 u metodi za dodavanje računa u okviru BillController-a potrebno je za izmenu broja 
// dostupnih/kupljenih ponuda pozvati odgovarajuću metodu servisa zaduženog za rad sa ponudama
	
// 2.3 u metodi za izmenu računa u okviru BillController-a potrebno je nakon što se račun proglasi 
// otkazanim za izmenu broja dostupnih/kupljenih ponuda pozvati odgovarajuću metodu servisa 
// zaduženog za rad sa ponudama
	
// 2.4 u servisu zaduženom za rad sa računima, napisati metodu koja za prosleđene datume vraća 
// račune koji se nalaze u datom periodu
// pozvati je u okviru metode BillController-a za pronalazaksvihračunakojisukreiraniu 
// odgovarajućemvremenskomperiodu	
	
// 3.1 ne dozvoliti brisanje onih kategorija za koje postoje neistekle ponude i računi
// napisati metodu u servisu zaduženom za rad sa ponudama koja proverava da li postoje 
// ponude za datu kategoriju (kategoriju koja želi da se obriše)
// napisati metodu u servisu zaduženom za rad sa računima koja proverava da li postoje 
// aktivni računi za datu kategoriju
// pozvati prethodno kreirane metode u metodi za brisanje kategorije u okviru CategoryController-a
	
// 3.2 kreirati REST endpoint koji omogućava upload slike za kreiranu ponudu
// putanja /project/offers/uploadImage/{id}
// metoda treba da vrati izmenjenu ponudu, a ukoliko je prosleđen ID nepostojeće ponude vratiti null
	
// 3.3 ukoliko se ponuda proglasi isteklom potrebno je otkazati sve račune koji sadrže tu ponudu
// u okviru servisa zaduženog za rad sa računima napisati metodu koja otkazuje sve račune
// odgovarajuće ponude pozvati je u okviru metode za promenu statusa ponude u OfferController-u	
	
// 4.1 omogućiti kreiranje vaučera kada se atribut računa paymentMade postavi na true
// u okviru servisa zaduženog za rad sa vaučerima, napisati metodu koja vrši kreiranje 
// vaučera na osnovu prosleđenog računa
// pozvati je u okviru metode za izmenu računa u BillController-u
	
// 4.2 omogućiti slanje kreiranog vaučera na mejl kupca
// kreirati servis zadužen za slanje email-a
// u okviru email servisa kreirati metodu za slanje emailporuke
// u okviru metode za kreiranje vaučera servisa, zaduženog za rad sa vaučerima, 
// pozvati metodu emailservisa vaučer poslati u vidu tabele
		
}
