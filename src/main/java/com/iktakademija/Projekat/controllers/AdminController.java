package com.iktakademija.Projekat.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktakademija.Projekat.entities.BillEntity;
import com.iktakademija.Projekat.entities.CategoryEntity;
import com.iktakademija.Projekat.entities.OfferEntity;
import com.iktakademija.Projekat.entities.UserEntity;
import com.iktakademija.Projekat.entities.VoucherEntity;
import com.iktakademija.Projekat.entities.enums.EOfferStatus;
import com.iktakademija.Projekat.entities.enums.EUserRole;
import com.iktakademija.Projekat.repositories.BillRepository;
import com.iktakademija.Projekat.repositories.CategoryRepository;
import com.iktakademija.Projekat.repositories.OfferRepository;
import com.iktakademija.Projekat.repositories.UserRepository;
import com.iktakademija.Projekat.repositories.VoucherRepository;

import net.andreinc.mockneat.unit.misc.Cars;
import net.andreinc.mockneat.unit.text.Markovs;
import net.andreinc.mockneat.unit.text.Words;
import net.andreinc.mockneat.unit.time.LocalDates;
import net.andreinc.mockneat.unit.user.Emails;
import net.andreinc.mockneat.unit.user.Names;
import net.andreinc.mockneat.unit.user.Passwords;
import net.andreinc.mockneat.unit.user.Users;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {

	@Autowired
	BillRepository billRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	OfferRepository offerRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	VoucherRepository voucherRepository;

	// Populate base
	@RequestMapping(method = RequestMethod.POST, path = "/add")
	public void poulateDataBase() {
		poulateUsers();	
		poulateCategories();
		populateOffers();
		poulateVouchers();		
		populateBills();
	}
	
	// Generate user entries to database.
	// Do not guarantee exact number of entries.
	private void poulateUsers() {
		
		// Declarations
		List<UserEntity> list;
		UserEntity user;
		String email;
		String username;		
		
		// Add customers to list
		for (int i = 0; i < 10; i++) {
			
			// Generate unique email
			email = Emails.emails().get();
			list = userRepository.findAllByEmail(email);
			if (list.size() > 0) continue;				
			
			// Generate unique username
			username = Users.users().get();
			list = userRepository.findAllByEmail(username);
			if (list.size() > 0) continue;
			
			// Generate other data
			user = new UserEntity();			
			user.setFirstName(Names.names().first().get());
			user.setLastName(Names.names().last().get());
			user.setEmail(email);
			user.setUsername(username);
			user.setPassword(Passwords.passwords().get());
			user.setUserRole(EUserRole.ROLE_CUSTOMER);
			
			// Add to database
			userRepository.save(user);
		}

		// Add sellerss to list
		for (int i = 0; i < 5; i++) {
			
			// Generate unique email
			email = Emails.emails().get();
			list = userRepository.findAllByEmail(email);
			if (list.size() > 0) continue;				
			
			// Generate unique username
			username = Users.users().get();
			list = userRepository.findAllByEmail(username);
			if (list.size() > 0) continue;
			
			// Generate other data
			user = new UserEntity();
			user.setFirstName(Names.names().first().get());
			user.setLastName(Names.names().last().get());
			user.setEmail(email);
			user.setUsername(username);
			user.setPassword(Passwords.passwords().get());
			user.setUserRole(EUserRole.ROLE_SELLER);
			
			// Add to database
			userRepository.save(user);
		}
		
		// Add admins to list
		for (int i = 0; i < 5; i++) {
			
			// Generate unique email
			email = Emails.emails().get();
			list = userRepository.findAllByEmail(email);
			if (list.size() > 0) continue;				
			
			// Generate unique username
			username = Users.users().get();
			list = userRepository.findAllByEmail(username);
			if (list.size() > 0) continue;
			
			// Generate other data
			user = new UserEntity();
			user.setFirstName(Names.names().first().get());
			user.setLastName(Names.names().last().get());
			user.setEmail(email);
			user.setUsername(username);
			user.setPassword(Passwords.passwords().get());
			user.setUserRole(EUserRole.ROLE_ADMIN);
			
			// Add to database
			userRepository.save(user);
		}
	}
	
	// Generate category entries to database.
	// Do not guarantee exact number of entries.
	private void poulateCategories() {
		
		// Declarations
		List<CategoryEntity> list;
		CategoryEntity category;
		String name;
		
		// Add customers to list
		for (int i = 0; i < 10; i++)
		{
			// Generate uniqe name and exit if exists			
			name = Cars.cars().brands().get();
			list = categoryRepository.findAllByName(name);
			if (list.size() > 0) continue;			
			
			// Generate new category and set name
			category = new CategoryEntity();
			category.setName(name);
			
			// Generate description text
			String dummy = Markovs.markovs().loremIpsum().get();
			dummy = dummy.substring(0, Math.min(250, dummy.length()));
			category.setDescription(dummy);
			
			// Add to database
			categoryRepository.save(category);
		}
	}	
	
	// Generate category entries to database.
	// Do not guarantee exact number of entries.
	private void poulateVouchers() {
		
		// Declarations
		VoucherEntity voucher;
		
		// Add customers to list
		for (int i = 0; i < 10; i++)
		{
			// Generate new category and set name
			voucher = new VoucherEntity();
			voucher.setUsed(false);		
			voucher.setExpirationDate(LocalDates.localDates().between(LocalDate.of(2018, 1, 1), LocalDate.of(2022, 12, 31)).get());
			
			voucher.setUser(userRepository.findRandom());
			OfferEntity a = offerRepository.findRandom();
			voucher.setOffer(a);
			
			// Add to database
			voucherRepository.save(voucher);
		}
	}	
	
	// Generate offers entries to database.
	// Do not guarantee exact number of entries.
	private void populateOffers()
	{
		
		// Declarations
		Random rnd = new Random();
		List<OfferEntity> list;
		OfferEntity offer;
		String name;
		Double price;
		
		// Add customers to list
		for (int i = 0; i < 10; i++)
		{
			// Generate uniqe name and exit if exists			
			name = Words.words().adverbs().get() + " " + Words.words().nouns().get();
			list = offerRepository.findAllByOfferName(name);
			if (list.size() > 0) continue;			
			
			// Generate new category and all data
			offer = new OfferEntity();
			offer.setOfferName(name);
			
			// Generate description text
			String dummy = Markovs.markovs().loremIpsum().get();
			dummy = dummy.substring(0, Math.min(50, dummy.length()));
			offer.setOfferDescription(dummy);
			
			offer.setOfferCreated(LocalDates.localDates().between(LocalDate.of(2005, 1, 1), LocalDate.of(2020, 12, 31)).get());
			
			// Make some expiration date as null
			if (rnd.nextInt(10) > 3) {
				offer.setOfferExpires(LocalDates.localDates().between(LocalDate.of(2021, 1, 1), LocalDate.of(2022, 12, 31)).get());
			} else offer.setOfferExpires(null);	
			
			// Set prices
			price = Math.round(rnd.nextDouble()*5000 + 5000 * 100) / 100.0;
			offer.setRegularPrice(price);
			price = Math.round(price* (1 - rnd.nextDouble()/20) * 100) / 100.0;
			offer.setActionPrice(price);
			
			// Set image to null
			offer.setImagePath(null);
			
			// Set quantities
			offer.setAvailableOffers(rnd.nextInt(3)+2);
			offer.setBoughtOffers(Math.max(rnd.nextInt(4)-2, 0));
			offer.setOfferStatus(EOfferStatus.WAIT_FOR_APPROVING);		
					
			offer.setCategory(categoryRepository.findRandom());
			offer.setUser(userRepository.findRandom());
			
			// Add to database
			offerRepository.save(offer);
		}
	}
	
	// Generate offers entries to database.
	// Do not guarantee exact number of entries.
	private void populateBills()
	{
		
		// Declarations
		BillEntity bill;
		
		// Add customers to list
		for (int i = 0; i < 10; i++)
		{
			// Generate new category and set name
			bill = new BillEntity();
			bill.setPaymentMade(true);
			bill.setPaymentCanceled(false);
			bill.setBillCreated(LocalDates.localDates().between(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 5, 31)).get());
			
			bill.setUserBillEntity(userRepository.findRandom());
			bill.setOfferBillEntity(offerRepository.findRandom());
			
			// Add to database
			billRepository.save(bill);
		}
	}
}
