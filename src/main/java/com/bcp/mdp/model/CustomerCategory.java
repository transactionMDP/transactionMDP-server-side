package com.bcp.mdp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerCategory implements Serializable{
	
	@Id
	@GeneratedValue()
	long idCustomerCategory;
	String type;
	
	@OneToMany(mappedBy="customerCategory")
	@JsonBackReference
	Set<Customer> customers;
	
	@OneToMany(mappedBy="customerCategory")
	@JsonBackReference
	Set<CustomerSubCategory> customerSubCategories;
	
	@OneToMany(mappedBy="customerCategory")
	@JsonBackReference
	Set<ExchangeRate> exchanges;
}
