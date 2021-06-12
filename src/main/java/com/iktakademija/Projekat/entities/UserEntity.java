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
import com.iktakademija.Projekat.entities.enums.EUserRole;

@Entity(name = "users")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer id;
	
	@Column(name = "name", nullable = false)
	protected String firstName;
	
	@Column(name = "lastname", nullable = false)
	protected String lastName;
	
	@Column(name = "username", unique = true, nullable = false)
	protected String username;
	
	@Column(name = "pass", nullable = false)
	protected String password;
	
	@Column(name = "email", unique = true, nullable = false)
	protected String email;
	
	@Column(name = "role")
	protected EUserRole userRole;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	protected List<OfferEntity> userOffers;
	
	@JsonIgnore
	@OneToMany(mappedBy = "userBillEntity", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)	
	private List<BillEntity> billsUserEntity;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<VoucherEntity> vouchers;
	
	@Version
	protected Integer version;	

	public UserEntity() {
		super();
	}	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EUserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(EUserRole userRole) {
		this.userRole = userRole;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public List<OfferEntity> getUserOffers() {
		return userOffers;
	}

	public void setUserOffers(List<OfferEntity> userOffers) {
		this.userOffers = userOffers;
	}

	public List<BillEntity> getBillsUserEntity() {
		return billsUserEntity;
	}

	public void setBillsUserEntity(List<BillEntity> billsUserEntity) {
		this.billsUserEntity = billsUserEntity;
	}

	public List<VoucherEntity> getVouchers() {
		return vouchers;
	}

	public void setVouchers(List<VoucherEntity> vouchers) {
		this.vouchers = vouchers;
	}
	
}
