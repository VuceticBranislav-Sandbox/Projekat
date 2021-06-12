package com.iktakademija.Projekat.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktakademija.Projekat.entities.CategoryEntity;
import com.iktakademija.Projekat.repositories.CategoryRepository;
import com.iktakademija.Projekat.services.BillEntityServiceImpl;
import com.iktakademija.Projekat.services.OfferEntityServiceImpl;

@RestController
@RequestMapping(path = "/project/categories")
public class CategoryController {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private OfferEntityServiceImpl offerEntityServiceImpl;
	
	@Autowired
	private BillEntityServiceImpl billEntityServiceImpl;
	
	// 2.3 kreirati REST endpoint koji vraća listu svih kategorija
	// putanja /project/categories
	@RequestMapping(method = RequestMethod.GET, path = "")
	public List<CategoryEntity> getAllCategories() {		
		
		// Retur list of all categories
		return categoryRepository.findAll();
	}
	
	// 2.4 kreirati REST endpoint koji omogućava dodavanje nove kategorije putanja /project/categories
	// metoda treba da vrati dodatu kategoriju
	@RequestMapping(method = RequestMethod.POST, path = "")
	public CategoryEntity addNewCategory(
			@RequestBody CategoryEntity category) {	
		
		// Trivial check
		if (category == null) return null;	
		
		// Check if new name is not null and do not exists in database		
		if (category.getName() == null || 
			categoryRepository.findByName(category.getName()) != null) return null;
		
		// Create object to add
		CategoryEntity newCategory = new CategoryEntity();
		if (category.getName() != null)
			newCategory.setName(category.getName());
		if (category.getDescription() != null) 
			newCategory.setDescription(category.getDescription());
		
		// Add to database
		return categoryRepository.save(newCategory);
	}	
	
	// 2.5 kreirati REST endpoint koji omogućava izmenu postojeće kategorije
	// putanja /project/categories/{id}
	// ukoliko je prosleđen ID koji ne pripada nijednoj kategoriji metoda treba da vrati null, a u suprotnom vraća podatke kategorije sa izmenjenim vrednostima
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public CategoryEntity updateCategory(
			@PathVariable(name = "id") Integer id, 
			@RequestBody CategoryEntity category) {

		// Trivial check
		if (id == null || category == null) return null;	
		
		// Find category. Exit if not found	
		Optional<CategoryEntity> op = categoryRepository.findById(id);
		if (op.isEmpty()) return null;
		CategoryEntity newCategory = op.get();
		
		// Check if new name is not null and do not exists in database		
		if (category.getName() == null || 
			categoryRepository.findByName(category.getName()) != null) return null;
		
		// Update object to add	
		if (category.getName() != null)
			newCategory.setName(category.getName());
		if (category.getDescription() != null)
			newCategory.setDescription(category.getDescription());

		return categoryRepository.save(newCategory);
	}
		
	// 2.6 kreirati REST endpoint koji omogućava brisanje postojeće kategorije
	// putanja /project/categories/{id}
	// ukoliko je prosleđen ID koji ne pripada nijednoj kategoriji metoda treba da vrati null, a u suprotnom vraća podatke o kategoriji koja je obrisana
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public CategoryEntity removeCategory(
			@PathVariable(name = "id") Integer id) {	
			
		// Trivial check
		if (id == null) return null;
		
		// Find category	
		Optional<CategoryEntity> op = categoryRepository.findById(id);
		if (op.isEmpty()) return null;
		CategoryEntity category = op.get();	
		
		
		// 3.1 ne dozvoliti brisanje onih kategorija za koje postoje neistekle ponude i računi
		// napisati metodu u servisu zaduženom za rad sa ponudama koja proverava da li postoje 
		// ponude za datu kategoriju (kategoriju koja želi da se obriše)
		// napisati metodu u servisu zaduženom za rad sa računima koja proverava da li postoje 
		// aktivni računi za datu kategoriju
		// pozvati prethodno kreirane metode u metodi za brisanje kategorije u okviru CategoryController-a		
		if (offerEntityServiceImpl.isCategoryUnused(id) && 
			billEntityServiceImpl.isCategoryUnused(id)) {
				
			// Unlink and remove category	
			categoryRepository.delete(category);
			return category;			
			}		
		return null;
	}
	
	// 2.7 kreirati REST endpoint koji vraća kategoriju po vrednosti prosleđenog ID-a
	// putanja /project/categories/{id}
	// u slučaju da ne postoji kategorija sa traženom vrednošću ID-a vratiti null
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public CategoryEntity getCategoriesById(@PathVariable(name = "id") Integer id) {

		// Trivial check
		if (id == null) return null;	
		
		// Return category by id
		return categoryRepository.findById(id).orElse(null);
	}

}
