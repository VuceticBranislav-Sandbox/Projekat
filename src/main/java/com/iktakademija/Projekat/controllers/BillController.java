package com.iktakademija.Projekat.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktakademija.Projekat.entities.BillEntity;
import com.iktakademija.Projekat.entities.OfferEntity;
import com.iktakademija.Projekat.entities.UserEntity;
import com.iktakademija.Projekat.entities.enums.EUserRole;
import com.iktakademija.Projekat.repositories.BillRepository;
import com.iktakademija.Projekat.repositories.CategoryRepository;
import com.iktakademija.Projekat.repositories.OfferRepository;
import com.iktakademija.Projekat.repositories.UserRepository;
import com.iktakademija.Projekat.services.BillEntityService;
import com.iktakademija.Projekat.services.OfferEntityService;
import com.iktakademija.Projekat.services.VoucherEntityService;

@RestController
@RequestMapping(path = "/project/bills")
public class BillController {
	
	@Autowired
	BillRepository billRepository;
	
	@Autowired
	UserRepository userRepository;	
	
	@Autowired
	OfferRepository offerRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	OfferEntityService offerEntityService;
	
	@Autowired
	BillEntityService billEntityService;
	
	@Autowired
	VoucherEntityService voucherEntityService;
	
	// 3.3 u paketu com.iktpreobuka.project.controllers napraviti klasu BillController sa 
	// REST endpoint-om koji vraća listu svih računa
	@RequestMapping(method = RequestMethod.GET, path = "")
	public List<BillEntity> getAllBills() {
		return billRepository.findAll();
	}
	
	// 3.6 kreirati REST endpoint-ove za dodavanje, izmenu i brisanje računa
	// putanja /project/bills/{offerId}/buyer/{buyerId} (dodavanje)
	// putanja /project/bills/{id} (izmena i brisanje)
	// 5.1 proširiti metodu za dodavanje računa tako da se smanji broj dostupnih ponuda ponude sa 
	// računa, odnosno poveća broj kupljenih
	@RequestMapping(value = "/{offerId}/buyer/{buyerId}", method = RequestMethod.POST)
	public BillEntity createBillReviseted(
			@PathVariable Integer offerId, 
			@PathVariable Integer buyerId) {
		
		// Trivial check
		if (offerId == null || buyerId == null) return null;
		
		// Find and validate offer
		Optional<OfferEntity> offerEntity = offerRepository.findById(offerId);
		if (offerEntity.isEmpty() || offerEntity.get().getAvailableOffers() <= 1) return null;
		
		// Find and validate user. User must be ROLE_CUSTOMER
		Optional<UserEntity> buyerEntity = userRepository.findById(buyerId);
		if (buyerEntity.isEmpty() || buyerEntity.get().getUserRole().equals(EUserRole.ROLE_CUSTOMER) == false) return null;
		
		// Prepare bill
		BillEntity billEntity = new BillEntity();
		billEntity.setBillCreated(LocalDate.now());
		billEntity.setPaymentCanceled(false);
		billEntity.setPaymentMade(false);			
		billEntity.setUserBillEntity(buyerEntity.get());
		billEntity.setOfferBillEntity(offerEntity.get());
		
		// 2.2 u metodi za dodavanje računa u okviru BillController-a potrebno je za izmenu broja 
		// dostupnih/kupljenih ponuda pozvati odgovarajuću metodu servisa zaduženog za rad sa ponudama	
		// Change offer availible amount
		offerEntityService.updateAvailableOffers(offerId, 1);

		// Add to database
		offerRepository.save(offerEntity.get());
		return billRepository.save(billEntity);	
	}
	
	// 3.6-2 kreirati REST endpoint-ove za dodavanje, izmenu i brisanje računa
	// putanja /project/bills/{offerId}/buyer/{buyerId} (dodavanje)
	// putanja /project/bills/{id} (izmena i brisanje)
	// 5.2 proširiti metodu za izmenu računa tako da ukoliko se račun proglašava otkazanim 
	// tada treba povećati broj dostupnih ponuda ponude sa računa, odnosno smanjiti broj kupljenih
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public BillEntity updateBillRevisited(
			@PathVariable Integer id, 
			@RequestParam Boolean paymentMade,
			@RequestParam Boolean paymentCanceled) {
		
		// Trivial check
		if (id == null || paymentMade == null || paymentCanceled == null) return null;
		
		// Find bill
		Optional<BillEntity> billEntity = billRepository.findById(id);

		// 
		if (billEntity.isEmpty()) return null;

		if (paymentMade != null) {
			if (billEntity.get().getPaymentMade() == false && paymentMade == true) {
				billEntity.get().setPaymentMade(paymentMade);
				voucherEntityService.createVoucherAfterPayment(billEntity.get());
			}
			else billEntity.get().setPaymentMade(paymentMade);
		}
		if (paymentCanceled == true) {
			billEntity.get().setPaymentCanceled(paymentCanceled);
			
			// 2.3 u metodi za izmenu računa u okviru BillController-a potrebno je nakon što se račun proglasi 
			// otkazanim za izmenu broja dostupnih/kupljenih ponuda pozvati odgovarajuću metodu servisa 
			// zaduženog za rad sa ponudama			
			offerEntityService.updateAvailableOffers(id, -1);
		}
		return billRepository.save(billEntity.get());
	}
	
	// 3.6 Remove
	@RequestMapping(method = RequestMethod.DELETE, path = "/{Id}")
	public BillEntity removeBill(
			@PathVariable(value = "Id") Integer idBill){
		
		// Trivial check
		if (idBill == null) return null;
		
		// Get valid offer and buyer
		Optional<BillEntity> op = billRepository.findById(idBill);
		if (op.isEmpty()) return null;
		BillEntity bill = op.get();
		
		bill.setOfferBillEntity(null);
		bill.setUserBillEntity(null);		
	
		billRepository.delete(bill);
		return bill;
	}	
	
	// 3.7 kreirati REST endpoint za pronalazak svih računa određenog kupca
	// putanja /project/bills/findByBuyer/{buyerId}
	@RequestMapping(method = RequestMethod.GET, path = "/findByBuyer/{buyerId}")
	public List<BillEntity> findByBuyer(@PathVariable(value = "buyerId") Integer id) 
	{
		// Trivial check
		if (id == null) return null;
		
		return billRepository.findAllbillsUserEntityById(id);		
	}
	
	// 3.8 kreirati REST endpoint za pronalazak svih računa određene kategorije
	// putanja /project/bills/findByCategory/{categoryId}
	@RequestMapping(method = RequestMethod.GET, path = "/findByCategory/{categoryId}")
	public List<BillEntity> findByCategory(@PathVariable(value = "categoryId") Integer id) 
	{
		// Trivial check
		if (id == null) return null;

		return billRepository.findByCategory(id);
	}		
	
	// 3.9 kreirati REST endpoint za pronalazak svih računa koji
	// su kreirani u odgovarajućem vremenskom periodu
	// putanja /project/bills/findByDate/{startDate}/and/{endDate}	 
	@RequestMapping(method = RequestMethod.GET, path = "/findByDate/{startDate}/and/{endDate}")
	public List<BillEntity> findByDate(
			@PathVariable(value = "startDate") @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
			@PathVariable(value = "endDate") @DateTimeFormat(iso = ISO.DATE) LocalDate endDate) 
	{
		// Trivial check
		if (startDate == null || endDate == null) return null;
		
		//return billRepository.findByBillCreatedBetweenOrderByBillCreatedAsc(startDate, endDate);
		return billEntityService.findBillBetweenDates(startDate, endDate);
	}
	// TODO posto je filter bolje query parameter
	
}
