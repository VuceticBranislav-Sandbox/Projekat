package com.iktakademija.Projekat.services;

import com.iktakademija.Projekat.entities.BillEntity;
import com.iktakademija.Projekat.entities.VoucherEntity;

public interface VoucherEntityService {
	
	// T4-4.1 Omogućiti kreiranje vaučera kada se atribut računa paymentMade postavi na true
	// u okviru servisa zaduženog za rad sa vaučerima, napisati metodu koja vrši kreiranje vaučera 
	// na osnovu prosleđenog računa pozvati je u okviru metode za izmenu računa u BillController-u
	public VoucherEntity createVoucherAfterPayment(BillEntity bill);

}
