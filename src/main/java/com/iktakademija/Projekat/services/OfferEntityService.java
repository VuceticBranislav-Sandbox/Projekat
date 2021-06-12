package com.iktakademija.Projekat.services;

public interface OfferEntityService {

	// 2.1 u servisu zaduženom za rad sa ponudama, napisati metodu koja za  
	// prosleđen ID ponude, vrši izmenu broja kupljenih/dostupnih ponuda	
	// public OfferEntity updateAvailableOffers(Integer id, Integer amount, Boolean isAvalible);
	public Boolean updateAvailableOffers(Integer id, Integer buyAmount);

}
