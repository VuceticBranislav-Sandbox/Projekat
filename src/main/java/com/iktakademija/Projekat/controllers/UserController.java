package com.iktakademija.Projekat.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktakademija.Projekat.entities.UserEntity;
import com.iktakademija.Projekat.entities.enums.EUserRole;
import com.iktakademija.Projekat.repositories.BillRepository;
import com.iktakademija.Projekat.repositories.OfferRepository;
import com.iktakademija.Projekat.repositories.UserRepository;
import com.iktakademija.Projekat.repositories.VoucherRepository;


@RestController
@RequestMapping(value= "/project/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;	
	
	@Autowired
	private OfferRepository offerRepository;	
	
	@Autowired
	private BillRepository billRepository;
	
	@Autowired
	private VoucherRepository voucherRepository;	
		
	// 1.3 kreirati REST endpoint koji vraća listu korisnika aplikacije putanja /project/users
	@RequestMapping(method = RequestMethod.GET, value = "")
	public List<UserEntity> getAllUsers() {
		
		// Return all users from base
		return userRepository.findAll();
	}	
	
	// 1.4 kreirati REST endpoint koji vraća korisnika po vrednosti prosleđenog ID-a
	// putanja /project/users/{id}
	// u slučaju da ne postoji korisniksa traženom vrednošću ID-a vratiti null
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public UserEntity getUserById(
			@PathVariable(name = "id") Integer id) {
		
		// Get all users by id in database		  
		return userRepository.findById(id).orElse(null);
	}	
	
	// 1.5 kreirati REST endpoint koji omogućava dodavanje novog korisnika. putanja /project/users
	// u okviru ove metode postavi vrednost atributa user role na ROLE_CUSTOMER metoda treba da vrati dodatog korisnika
	@RequestMapping(method = RequestMethod.POST, value = "")
	public UserEntity addNewUser(
			@RequestBody UserEntity user) {
		
		// Trivial check
		if (user == null) return null;
		
		// Check if new username and email is not null and do not exists in database		
		if (user.getUsername() == null || user.getEmail() == null ||
			userRepository.findAllByUsername(user.getUsername()).isEmpty() == false ||
			userRepository.findAllByEmail(user.getEmail()).isEmpty() == false) return null;

		// Setup new user and add to database
		UserEntity newUser = new UserEntity();
		if (user.getFirstName() != null) newUser.setFirstName(user.getFirstName());
		if (user.getLastName() != null)	newUser.setLastName(user.getLastName());
		if (user.getUsername() != null)	newUser.setUsername(user.getUsername());
		if (user.getPassword() != null)	newUser.setPassword(user.getPassword());
		if (user.getEmail() != null)	newUser.setEmail(user.getEmail());		
		newUser.setUserRole(EUserRole.ROLE_CUSTOMER);	
		
		// Return new user
		return userRepository.save(newUser);		
	}

	// 1.6 kreirati REST endpoint koji omogućava izmenu postojećeg korisnika. putanja /project/users/{id}
	// ukoliko je prosleđen ID koji ne pripada nijednom korisniku metoda treba da vrati null, a u suprotnom vraća podatke korisnika sa izmenjenim vrednostima
	// NAPOMENA: u okviru ove metodene menjati vrednost atributa user role i password
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public UserEntity updateUser(
			@PathVariable(name = "id") Integer id, 
			@RequestBody UserEntity user) {

		// Trivial checks
		if (id == null || user == null) return null;	
		
		// Find user. Exit if not found	
		Optional<UserEntity> op = userRepository.findById(id);		
		if (op.isEmpty()) return null;
		UserEntity existingUser = op.get();		
		
		// Check if new username and email is not null and do not exists in database	
		// TODO check this in details
		if (user.getUsername() != null) {
			List<UserEntity> list = userRepository.findAllByUsername(user.getUsername());
			if (list.size() > 1) return null;			
			if (list.size() == 1) {				
				if (list.get(0).getId() != id.intValue()) return null;
			}
		}
		if (user.getEmail() != null) {
			List<UserEntity> list = userRepository.findAllByEmail(user.getEmail());
			if (list.size() > 1) return null;			
			if (list.size() == 1) {				
				if (list.get(0).getId() != id.intValue()) return null;
			}
		}
		
		// Update existing user and return to database
		if (user.getFirstName() != null) existingUser.setFirstName(user.getFirstName());
		if (user.getLastName() != null)	existingUser.setLastName(user.getLastName());
		if (user.getUsername() != null)	existingUser.setUsername(user.getUsername());
		if (user.getPassword() != null)	existingUser.setPassword(user.getPassword());
		if (user.getEmail() != null) existingUser.setEmail(user.getEmail());	
		if (user.getUserRole() != null)	existingUser.setUserRole(user.getUserRole());	

		return userRepository.save(existingUser);					
	}		
	
	//1.7 kreirati REST endpoint koji omogućava izmenu atributa user_role postojećeg korisnika
	//putanja /project/users/change/{id}/role/{role}
	//ukoliko je prosleđen ID koji ne pripada nijednom korisniku metoda treba da vrati null, a u suprotnom vraća podatke korisnika sa izmenjenom vrednošću atributa user role
	@RequestMapping(method = RequestMethod.PUT, path = "/change/{id}/role/{role}")
	public UserEntity changeUserRole(
			@PathVariable(name = "id") Integer id, 
			@PathVariable(name = "role") EUserRole role) {
		
		// Trivial checks
		if (id == null) return null;
		
		// Find user		
		Optional<UserEntity> op = userRepository.findById(id);
		
		// Exit if user do not exist
		UserEntity user = op.orElse(null);
		if (user == null) return null;
		
		// Change role if user exists
		user.setUserRole(role);		
		return userRepository.save(user);
	}
	
	//1.8 kreirati REST endpoint koji omogućava izmenu vrednosti atributa password postojećeg korisnika
	//putanja /project/users/changePassword/{id}
	//kao RequestParam proslediti vrednosti stare i nove lozinke
	//ukoliko je prosleđen ID koji ne pripada nijednom korisniku metoda treba da vrati null, a u suprotnom vraća podatke korisnikasa izmenjenom vrednošću atributa password
	//NAPOMENA: da bi vrednost atributa password mogla da bude zamenjenasa novom vrednošću, neophodno je da se vrednost stare lozinke korisnika poklapa sa vrednošću stare lozinke prosleđene kao RequestParam
	@RequestMapping(value = "/changePassword/{id}", method = RequestMethod.PUT)
	public UserEntity changePassword(
			@PathVariable(name = "id") Integer id, 
			@RequestParam(name = "old") String oldPass, 
			@RequestParam(name = "new") String newPass) {

		// Trivial checks
		if (id == null) return null;
		
		// Find user	
		Optional<UserEntity> op = userRepository.findById(id);
		
		// Get user by id and return null if not found		
		if (op.isEmpty()) return null;
		UserEntity user = op.orElse(null);

		// Check is passwords parametere valid
		if (oldPass == null || newPass == null) return user;

		// Check is old pasword match
		if (user.getPassword().equals(oldPass) == false) return user;

		user.setPassword(newPass);
		return userRepository.save(user);
	}

	//1.9 kreirati REST endpoint koji omogućava brisanje postojećeg korisnika. putanja /project/users/{id}
	//ukoliko je prosleđen ID koji ne pripada nijednom korisniku metoda treba da vrati null, a u suprotnom vraća podatke o korisniku koji je obrisan
	@RequestMapping(method = RequestMethod.DELETE, path = "{id}")
	public UserEntity removeUser(
			@PathVariable(name = "id") Integer id) {
		
		// Trivial checks
		if (id == null) return null;
		
		// Find user	
		Optional<UserEntity> op = userRepository.findById(id);
		
		// Get user from base	
		if (op.isEmpty()) return null;
		UserEntity user = op.orElse(null);
		
		// Unlink and remove user
		// TODO What to do with unlinked stuff?
		offerRepository.nullColumnUseroffers(id.toString());	
		billRepository.nullColumnUser(id.toString());
		voucherRepository.nullColumnUser(id.toString());
		userRepository.delete(user);
		return user;
	}
	
	//1.10 kreirati REST endpoint koji vraća korisnika po vrednosti prosleđenog username-a
	//putanja /project/users/by-username/{username}
	//u slučaju da ne postoji korisnik sa traženim username-om vratiti null
	@RequestMapping(method = RequestMethod.GET, path = "/by-username/{username}")
	public UserEntity getUserByUsername(
			@PathVariable String username) {

		// Trivial checks
		if (username == null) return null;
		
		Optional<UserEntity> op = userRepository.findByUsername(username);		
		return op.orElse(null);
	}	

}

