package com.iktakademija.Projekat.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktakademija.Projekat.entities.OfferEntity;
import com.iktakademija.Projekat.entities.UserEntity;
import com.iktakademija.Projekat.entities.VoucherEntity;
import com.iktakademija.Projekat.entities.enums.EUserRole;
import com.iktakademija.Projekat.repositories.OfferRepository;
import com.iktakademija.Projekat.repositories.UserRepository;
import com.iktakademija.Projekat.repositories.VoucherRepository;

@RestController
@RequestMapping(path = "/project/vouchers")
public class VoucherController {

	@Autowired
	VoucherRepository voucherRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OfferRepository offerRepository;

	// 4.3 u paketu com.iktpreobuka.project.controllers napraviti klasu VoucherController 
	// sa REST endpoint-om koji vraća listu svih vaučera
	// putanja /project/vouchers
	@RequestMapping(method = RequestMethod.GET, path = "")
	public List<VoucherEntity> getAllVouchers() {
		return voucherRepository.findAll();
	}

	// 4.6 kreirati REST endpoint-ove za dodavanje, izmenu i brisanje vaučera
	// putanja /project/vouchers/{offerId}/buyer/{buyerId}
	// NAPOMENA: samo korisnik sa ulogom ROLE_CUSTOMER se može naći kao kupac na 
	// vaučeru (u suprotnom ne dozvoliti kreiranje vaučera)
	// putanja /project/vouchers/{id} (izmena)
	// putanja /project/vouchers/{id} (brisanje)

	// 4.6 Add new
	@RequestMapping(method = RequestMethod.POST, path = "/{offerId}/buyer/{buyerId}")
	public VoucherEntity addNewVoucher(
			@PathVariable Integer offerId,
			@PathVariable Integer buyerId) {

		VoucherEntity voucherEntity = new VoucherEntity();
		Optional<UserEntity> buyerEntity = userRepository.findById(buyerId);
		Optional<OfferEntity> offerEntity = offerRepository.findById(offerId);

		if (offerEntity.isEmpty() || buyerEntity.isEmpty()
				|| buyerEntity.get().getUserRole().equals(EUserRole.ROLE_CUSTOMER) == false)
			return null;
		
		voucherEntity.setExpirationDate(LocalDate.now().plusDays(20));
		voucherEntity.setUsed(false);
		voucherEntity.setUser(buyerEntity.get());
		voucherEntity.setOffer(offerEntity.get());
		return voucherRepository.save(voucherEntity);
	}

	// 4.6 Update
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public VoucherEntity updateVoucher(
			@PathVariable Integer id, 
			@RequestBody VoucherEntity voucher) {

		Optional<VoucherEntity> op = voucherRepository.findById(id);
		if (op.isEmpty()) return null;
		VoucherEntity voucherEntity = op.get();
		
		if (voucher.getUsed() != null) voucherEntity.setUsed(voucher.getUsed());
		if (voucher.getExpirationDate() != null) voucherEntity.setExpirationDate(voucher.getExpirationDate());
		// TODO clear user and offer on update? Same for all updates
		
		return voucherRepository.save(voucherEntity);
	}

	// 4.6 Remove
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public VoucherEntity deleteVoucher(
			@PathVariable Integer id) {
		
		Optional<VoucherEntity> op = voucherRepository.findById(id);
		if (op.isEmpty()) return null;
		VoucherEntity voucherEntity = op.get();
		
		// TODO remove connections with 
		voucherEntity.setUsed(null);
		voucherEntity.setOffer(null);	
		
		voucherRepository.delete(voucherEntity);
		return voucherEntity;
	}

	// 4.7 kreirati REST endpoint za pronalazak svih vaučera određenog kupca
	// putanja /project/vouchers/findByBuyer/{buyerId}
	@RequestMapping(method = RequestMethod.GET, path = "/findByBuyer/{buyerId}")
	public List<VoucherEntity> getVouchersByUser(
			@PathVariable Integer buyerId) {
		
		Optional<UserEntity> buyer = userRepository.findById(buyerId);
		
		if (buyer.isEmpty()) return null;
		return voucherRepository.findByUser(buyer.get());
	}

	// 4.8 kreirati REST endpoint za pronalazak svih vaučera
	// određene ponude
	// putanja /project/vouchers/findByOffer/{offerId}
	@RequestMapping(method = RequestMethod.GET, path = "/findByOffer/{offerId}")
	public List<VoucherEntity> getVouchersByOffer(@PathVariable Integer offerId) {		
		
		Optional<OfferEntity> offers = offerRepository.findById(offerId);
		if (offers.isEmpty()) return null; 
		return voucherRepository.findByOffer(offers.get());			
	}
	
	// 4.9 kreirati REST endpoint za pronalazak svih vaučera
	// koji nisu istekli
	// putanja /project/vouchers/findNonExpiredVoucher
	@RequestMapping(method = RequestMethod.GET, path = "/findNonExpiredVoucher")
	public List<VoucherEntity> findValid(LocalDate now) {
		now = LocalDate.now();
		return voucherRepository.findByExpirationDateGreaterThanEqual(now);
	}
	
}
