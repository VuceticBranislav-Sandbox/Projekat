package com.iktakademija.Projekat.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iktakademija.Projekat.entities.enums.EOfferStatus;

@Entity(name = "offers")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class OfferEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer id;
	
	@Column(name = "name", nullable = false, unique = true)
	protected String offerName;
	
	@Column(name = "descr")
	protected String offerDescription;
	
	@Column(name = "datecre", nullable = false)
	@DateTimeFormat(iso = ISO.DATE)
	protected LocalDate offerCreated;
	
	@Column(name = "dateexp")
	@DateTimeFormat(iso = ISO.DATE)
	protected LocalDate offerExpires;
	
	@Column(name = "pricereg", nullable = false)
	protected Double regularPrice;
	
	@Column(name = "priceact")
	protected Double actionPrice;
	
	@Column(name = "img")
	protected String imagePath;
	
	@Column(name = "offeavail", nullable = false)
	protected Integer availableOffers;
	
	@Column(name = "offbought")
	protected Integer boughtOffers;	
	
	@Column(name = "offstat", nullable = false)
	protected EOfferStatus offerStatus;	
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "cateoffers")	
	private CategoryEntity category;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "useroffers")	
	private UserEntity user;

	@JsonIgnore
	@OneToMany(mappedBy = "offerBillEntity", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)	
	private List<BillEntity> billsOffers;
	
	@JsonIgnore
	@OneToMany(mappedBy = "offer", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<VoucherEntity> vouchers;	
	
	@Version
	protected Integer version;	
	
	public OfferEntity() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getOfferDescription() {
		return offerDescription;
	}

	public void setOfferDescription(String offerDescription) {
		this.offerDescription = offerDescription;
	}

	public LocalDate getOfferCreated() {
		return offerCreated;
	}

	public void setOfferCreated(LocalDate offerCreated) {
		this.offerCreated = offerCreated;
	}

	public LocalDate getOfferExpires() {
		return offerExpires;
	}

	public void setOfferExpires(LocalDate offerExpires) {
		this.offerExpires = offerExpires;
	}

	public Double getRegularPrice() {
		return regularPrice;
	}

	public void setRegularPrice(Double regularPrice) {
		this.regularPrice = regularPrice;
	}

	public Double getActionPrice() {
		return actionPrice;
	}

	public void setActionPrice(Double actionPrice) {
		this.actionPrice = actionPrice;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Integer getAvailableOffers() {
		return availableOffers;
	}

	public void setAvailableOffers(Integer availableOffers) {
		this.availableOffers = availableOffers;
	}

	public Integer getBoughtOffers() {
		return boughtOffers;
	}

	public void setBoughtOffers(Integer boughtOffers) {
		this.boughtOffers = boughtOffers;
	}

	public EOfferStatus getOfferStatus() {
		return offerStatus;
	}

	public void setOfferStatus(EOfferStatus offerStatus) {
		this.offerStatus = offerStatus;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}	
	
	public List<BillEntity> getBillsOffers() {
		return billsOffers;
	}

	public void setBillsOffers(List<BillEntity> billsOffers) {
		this.billsOffers = billsOffers;
	}

	public List<VoucherEntity> getVouchers() {
		return vouchers;
	}

	public void setVouchers(List<VoucherEntity> vouchers) {
		this.vouchers = vouchers;
	}
	
}
