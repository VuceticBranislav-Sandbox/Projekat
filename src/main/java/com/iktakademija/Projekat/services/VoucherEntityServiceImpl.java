package com.iktakademija.Projekat.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktakademija.Projekat.dto.EmailObject;
import com.iktakademija.Projekat.entities.BillEntity;
import com.iktakademija.Projekat.entities.VoucherEntity;
import com.iktakademija.Projekat.repositories.VoucherRepository;

@Service
public class VoucherEntityServiceImpl implements VoucherEntityService {
	
	@Autowired
	VoucherRepository voucherRepository;
	
	@Autowired
	EmailService emailService;
	
	// T4-4.1 Omogućiti kreiranje vaučera kada se atribut računa paymentMade postavi na true
	// u okviru servisa zaduženog za rad sa vaučerima, napisati metodu koja vrši kreiranje vaučera 
	// na osnovu prosleđenog računa pozvati je u okviru metode za izmenu računa u BillController-u
	@Override
	public VoucherEntity createVoucherAfterPayment(BillEntity bill) {
		
		// Trivial check
		if (bill == null) return null;
		
		// Create new voucer
		VoucherEntity voucherEntity = new VoucherEntity();		
		voucherEntity.setExpirationDate(LocalDate.now().plusDays(10));
		voucherEntity.setUsed(false);
		voucherEntity.setUser(bill.getUserBillEntity());
		voucherEntity.setOffer(bill.getOfferBillEntity());
		
		// Update database
		voucherRepository.save(voucherEntity);
		
		// Send email		
		EmailObject object = new EmailObject();
		object.setTo(bill.getUserBillEntity().getEmail());
		object.setSubject("bill.getUserBillEntity().getEmail()");		
		object.setName(bill.getUserBillEntity().getFirstName());
		object.setLastname(bill.getUserBillEntity().getLastName());
		object.setOffer(bill.getOfferBillEntity().getId());
		object.setPrice(bill.getOfferBillEntity().getActionPrice());		
		object.setDate(voucherEntity.getExpirationDate());
		try {
			emailService.sendTemplateMessage(object);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		// Return new voucer
		return voucherEntity;
	}

}
