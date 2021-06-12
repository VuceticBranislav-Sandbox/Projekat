package com.iktakademija.Projekat.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "categories")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class CategoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer id;

	@Column(name = "name", nullable = false, unique = true)
	protected String name;

	@Column(name = "descr")
	protected String description;

	@JsonIgnore
	@OneToMany(mappedBy = "category", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	protected List<OfferEntity> cateOffers;
	
	@Version
	protected Integer version;	

	public CategoryEntity() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public List<OfferEntity> getCateOffers() {
		return cateOffers;
	}

	public void setCateOffers(List<OfferEntity> cateOffers) {
		this.cateOffers = cateOffers;
	}

}
