package com.iktakademija.Projekat.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "bills")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class BillEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer id;
	
	@Column(name = "pmade", nullable = false)
	protected Boolean paymentMade;
	
	@Column(name = "pcancel", nullable = false)
	protected Boolean paymentCanceled;
	
	@Column(name = "crdate", nullable = false)
	@DateTimeFormat(iso = ISO.DATE)
	protected LocalDate billCreated;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "offer")
	private OfferEntity offerBillEntity;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	private UserEntity userBillEntity;
	
	@Version
	protected Integer version;

	public BillEntity() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean isPaymentMade() {
		return paymentMade;
	}

	public void setPaymentMade(Boolean paymentMade) {
		this.paymentMade = paymentMade;
	}

	public Boolean isPaymentCanceled() {
		return paymentCanceled;
	}

	public void setPaymentCanceled(Boolean paymentCanceled) {
		this.paymentCanceled = paymentCanceled;
	}

	public LocalDate getBillCreated() {
		return billCreated;
	}

	public void setBillCreated(LocalDate billCreated) {
		this.billCreated = billCreated;
	}

	public OfferEntity getOfferBillEntity() {
		return offerBillEntity;
	}

	public void setOfferBillEntity(OfferEntity offerBillEntity) {
		this.offerBillEntity = offerBillEntity;
	}

	public UserEntity getUserBillEntity() {
		return userBillEntity;
	}

	public void setUserBillEntity(UserEntity userBillEntity) {
		this.userBillEntity = userBillEntity;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Boolean getPaymentMade() {
		return paymentMade;
	}

	public Boolean getPaymentCanceled() {
		return paymentCanceled;
	}	
}
