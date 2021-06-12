package com.iktakademija.Projekat.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iktakademija.Projekat.entities.CategoryEntity;
import com.iktakademija.Projekat.entities.OfferEntity;
import com.iktakademija.Projekat.entities.enums.EOfferStatus;
import com.iktakademija.Projekat.entities.enums.EUserRole;
import com.iktakademija.Projekat.repositories.BillRepository;
import com.iktakademija.Projekat.repositories.CategoryRepository;
import com.iktakademija.Projekat.repositories.OfferRepository;
import com.iktakademija.Projekat.repositories.UserRepository;
import com.iktakademija.Projekat.repositories.VoucherRepository;
import com.iktakademija.Projekat.services.BillEntityService;
import com.iktakademija.Projekat.services.FileUploadService;

@RestController
@RequestMapping(path = "/project/offers")
public class OfferController {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private BillRepository billRepository;
	
	@Autowired
	private VoucherRepository voucherRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BillEntityService billEntityService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	// 3.3 kreirati REST endpoint koja vraća listu svih ponuda. putanja /project/offers
	@RequestMapping(method = RequestMethod.GET, path = "")
	public List<OfferEntity> getAllOffers() {
		
		// Get all offers
		return offerRepository.findAll();
	}
	
	// 3.4 kreirati REST endpoint koji omogućava dodavanje nove ponude
	// putanja /project/offers
	// metoda treba da vrati dodatu ponudu
	@RequestMapping(method = RequestMethod.POST, path = "")
	public OfferEntity addNewOffer(
			@RequestBody OfferEntity offer) {

		// Exit if nothing to add
		if (offer == null) return null;
		
		// Check if new name is not null and do not exists in database		
		if (offer.getOfferName() == null || 
			categoryRepository.findByName(offer.getOfferName()) != null) return null;
	
		// Create object to add
		// TODO make additional checks where needed
		OfferEntity newOffer = new OfferEntity();
		if (offer.getOfferName() != null) newOffer.setOfferName(offer.getOfferName());
		if (offer.getOfferDescription() != null) newOffer.setOfferDescription(offer.getOfferDescription());
		if (offer.getOfferCreated() != null) newOffer.setOfferCreated(offer.getOfferCreated());
		if (offer.getOfferExpires() != null) newOffer.setOfferExpires(offer.getOfferExpires());
		if (offer.getRegularPrice() != null) newOffer.setRegularPrice(offer.getRegularPrice());
		if (offer.getActionPrice() != null) newOffer.setActionPrice(offer.getActionPrice());
		if (offer.getImagePath() != null) newOffer.setImagePath(offer.getImagePath());
		if (offer.getAvailableOffers() != null) newOffer.setAvailableOffers(offer.getAvailableOffers());
		if (offer.getBoughtOffers() != null) newOffer.setBoughtOffers(offer.getBoughtOffers());
		if (offer.getOfferStatus() != null) newOffer.setOfferStatus(offer.getOfferStatus());
		// if (offer.getCategory() != null) newOffer.setCategory(offer.getCategory());		
		// if (offer.getUser() != null) newOffer.setUser(offer.getUser());
		
		// Add new offer to database 
		return offerRepository.save(newOffer);
	}	
	
	// 3.5 kreirati REST endpoint koji omogućava izmenu postojeće ponude
	// putanja /project/offers/{id}
	// ukoliko je prosleđen ID koji ne pripada nijednoj ponuditreba da vrati null, a u suprotnom vraća podatke ponudesa izmenjenim vrednostima
	// NAPOMENA: u okviru ove metodene menjati vrednost atributa offer status
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public OfferEntity updateOffer(
			@PathVariable(name = "id") Integer id, 
			@RequestBody OfferEntity offer) {

		// Trivial check
		if (id == null || offer == null) return null;		
		
		// Find by offer id	
		Optional<OfferEntity> op = offerRepository.findById(id);   
		if (op.isEmpty()) return null;
		OfferEntity newOffer = op.get();

		// Check if new username and email is not null and do not exists in database	
		// TODO check this in details
		if (offer.getOfferName() != null) {
			List<OfferEntity> list = offerRepository.findAllByOfferName(offer.getOfferName());
			if (list.size() > 1) return null;			
			if (list.size() == 1) {				
				if (list.get(0).getId() != id.intValue()) 
					return null;
			}
		}
		
		// Create object to add and update existing offer and return to database
		// TODO make additional checks where needed
		if (offer.getOfferName() != null) newOffer.setOfferName(offer.getOfferName());
		if (offer.getOfferDescription() != null) newOffer.setOfferDescription(offer.getOfferDescription());
		if (offer.getOfferCreated() != null) newOffer.setOfferCreated(offer.getOfferCreated());
		if (offer.getOfferExpires() != null) newOffer.setOfferExpires(offer.getOfferExpires());
		if (offer.getRegularPrice() != null) newOffer.setRegularPrice(offer.getRegularPrice());
		if (offer.getActionPrice() != null) newOffer.setActionPrice(offer.getActionPrice());
		if (offer.getImagePath() != null) newOffer.setImagePath(offer.getImagePath());
		if (offer.getAvailableOffers() != null) newOffer.setAvailableOffers(offer.getAvailableOffers());
		if (offer.getBoughtOffers() != null) newOffer.setBoughtOffers(offer.getBoughtOffers());
		if (offer.getOfferStatus() != null) newOffer.setOfferStatus(offer.getOfferStatus());
		// if (offer.getCategory() != null) newOffer.setCategory(offer.getCategory());		
		// if (offer.getUser() != null) newOffer.setUser(offer.getUser());

		return offerRepository.save(newOffer);		
	}

	// 3.6 kreirati REST endpoint koji omogućava brisanje postojeće ponude
	// putanja /project/offers/{id}
	// ukoliko je prosleđen ID koji ne pripada nijednoj ponudi metoda treba da vrati null, a u suprotnom vraća podatke o ponudikoja je obrisana
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public OfferEntity getAddOffer(
			@PathVariable(name = "id") Integer id) {
		
		// Trivial check
		if (id == null) return null;

		// Find by offer id	
		Optional<OfferEntity> op = offerRepository.findById(id);   
		if (op.isEmpty()) return null;
		OfferEntity offer = op.get();
		
		voucherRepository.nullColumnOffer(id.toString());
		billRepository.nullColumnOffer(id.toString());
		offerRepository.delete(offer);
		return offer;		
	}	
	
	// 3.7 kreirati REST endpoint koji vraća ponudu po vrednosti prosleđenog ID-a
	// putanja /project/offers/{id}
	// u slučaju da ne postoji ponudasa traženom vrednošću ID-a vratiti null
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public OfferEntity getOfferById(
			@PathVariable(name = "id") Integer id) {
		
		// Trivial check
		if (id == null) return null;
		
		// Find offer by id	
		return offerRepository.findById(id).orElse(null);		
	}
	
	// 3.8 kreirati REST endpoint koji omogućava promenu vrednosti atributa offer status postojeće ponude
	// putanja /project/offers/changeOffer/{id}/status/{status}
	// ukoliko je prosleđen ID koji ne pripada nijednoj ponudi metoda treba da vrati null, a u suprotnom vraća podatke o ponudikoja je obrisana
	@RequestMapping(method = RequestMethod.PUT, path = "/changeOffer/{id}/status/{status}")
	public OfferEntity updateOffer(
			@PathVariable(name = "id") Integer id,
			@PathVariable(name = "status") EOfferStatus status) {
		
		// Trivial check
		if (id == null || status == null) return null;
		
		// Find by offer id	
		Optional<OfferEntity> op = offerRepository.findById(id);   
		if (op.isEmpty()) return null;
		
		// Find offer in base and change offer status
		OfferEntity offer = op.get();
		offer.setOfferStatus(status);	
		if (status == EOfferStatus.EXPIRED) billEntityService.cancelAllBillsForExpiredOffer(offer);
		return offerRepository.save(offer);		
	}			
	
	// 3.9 kreirati REST endpoint koji omogućava pronalazak svih ponuda čijase akcijska cena nalazi u odgovarajućem rasponu
	// putanja /project/offers/findByPrice/{lowerPrice}/and/{upperPrice}
	@RequestMapping(method = RequestMethod.GET, path = "/findByPrice/{lowerPrice}/and/{upperPrice}")
	public List<OfferEntity> findInPriceRange(
			@PathVariable(name = "lowerPrice") Double lowerPrice, 
			@PathVariable(name = "upperPrice") Double upperPrice) {
		
		// URL GET http://localhost:8090/project/offers/findByPrice/1000.0/and/10000.0
		
		// Trivial check
		if (lowerPrice == null || upperPrice == null) return null;

		return offerRepository.findByRegularPriceBetweenOrderByRegularPriceAsc(lowerPrice, upperPrice);
	}
	
	// 2.3 omogućiti dodavanje kategorije i korisnika koji je kreirao ponudu
	// izmeniti prethodnu putanju za dodavanje ponude
	// putanja/project/offers/{categoryId}/seller/{sellerId}
	// NAPOMENA: samo korisnik sa ulogom ROLE_SELLER imapravoda bude postavljen kao onaj koje 
	// kreirao/napravio ponudu (u suprotnomne dozvoliti kreiranje ponude); Kao datum 
	// kreiranja ponude postaviti trenutni datum i ponuda ističe za 10 dana od dana kreiranja
	@RequestMapping(path = "{categoryId}/seller/{sellerId}", method = RequestMethod.POST)
	public OfferEntity createNewOffer(
			@RequestBody OfferEntity newOffer, 
			@PathVariable Integer categoryId,
			@PathVariable Integer sellerId
//			@RequestBody OfferEntity newOffer, 
//			@RequestParam Integer sellerId, 
//			@RequestParam Integer categoryId
			) {
		
//		// Trivial check
//		if (categoryId == null || sellerId == null) return null;
//		
//		// Find by user id	
//		Optional<UserEntity> opU = userRepository.findById(sellerId);   
//		if (opU.isEmpty()) return null;		
//		
//		UserEntity user = opU.get();
//		if (user.getUserRole() != EUserRole.ROLE_SELLER) return null;
//		
//		// Find by category id	
//		Optional<CategoryEntity> opC = categoryRepository.findById(categoryId);   
//		if (opC.isEmpty()) return null;	
//		
//		CategoryEntity category = opC.get();
//		newOffer.setCategory(category);
//		newOffer.setUser(user);	
//		newOffer.setOfferCreated(LocalDate.now());
//		newOffer.setOfferExpires(LocalDate.now().plusDays(10));		
//		return offerRepository.save(newOffer);	
		
		if (userRepository.findById(sellerId).get().getUserRole().equals(EUserRole.ROLE_SELLER) == false) return null;
		OfferEntity offerEntity = new OfferEntity();
		offerEntity.setOfferName(newOffer.getOfferName());
		offerEntity.setOfferDescription(newOffer.getOfferDescription());
		offerEntity.setRegularPrice(newOffer.getRegularPrice());
		offerEntity.setOfferCreated(LocalDate.now()); 
		offerEntity.setImagePath(newOffer.getImagePath());
		offerEntity.setCategory(categoryRepository.findById(categoryId).get());
		offerEntity.setUser(userRepository.findById(sellerId).get());
		if (newOffer.getBoughtOffers() != null) {
			offerEntity.setAvailableOffers(newOffer.getBoughtOffers());
		} else {
			offerEntity.setAvailableOffers(1);// set min required available offers
		}
		offerEntity.setBoughtOffers(0);
		offerEntity.setOfferStatus(EOfferStatus.WAIT_FOR_APPROVING);// default status
		return offerRepository.save(offerEntity);			
	}	
	
	// 2.4 omogućiti izmenu kategorije ponude
	// izmeniti prethodnu putanju za izmenu ponude
	// putanja /project/offers/{id}/category/{categoryId}. Uradeno bez /category/
	@RequestMapping(method = RequestMethod.PUT, path = "{id}/{categoryId}")
	public OfferEntity changeCategory(
			@PathVariable(name = "id") Integer id, 
			@PathVariable(name = "categoryId") Integer Idcat) {

		Optional<OfferEntity> oe = offerRepository.findById(id);
		if (oe.isEmpty()) return null;
		
		Optional<CategoryEntity> ce = categoryRepository.findById(Idcat);
		if (ce.isEmpty()) return null;
		
		// Set category
		oe.get().setCategory(ce.get());
		return offerRepository.save(oe.get());	
	}
		
	// T4-3.2 kreirati REST endpoint koji omogućava upload slike za kreiranu ponudu
	// putanja /project/offers/uploadImage/{id}
	// metoda treba da vrati izmenjenu ponudu, a ukoliko je prosleđen ID nepostojeće ponude vratiti null
	@RequestMapping(method = RequestMethod.POST, path = "/uploadImage/{id}")
	public OfferEntity uploadImageForOffer(
			@RequestParam(name = "file") MultipartFile file, 
			RedirectAttributes redirectAttributes, 
			@PathVariable(name = "id") Integer id) {

		// Trivial check
		if (id == null || file == null) return null;
		
		// Check do offer exists and get it. Otherwise return null
		Optional<OfferEntity> op = offerRepository.findById(id);
		if (op.isEmpty()) return null;
		OfferEntity offer = op.get();
		
		// Try to save uploaded image to server
		// TODO chack file format and validate image	

		
		String retVal = null;

		try {
			retVal = fileUploadService.uploadOfferImage(file, redirectAttributes);
			if (retVal == null || retVal == "") return offer;
			offer.setImagePath(retVal);
		} catch (IOException e) {
			e.printStackTrace(); 
			// TODO redirektovati na stranicu sa porukom o gresci
		}

		// Return service retVal
		offerRepository.save(offer);
		return offer;
	}
	
}