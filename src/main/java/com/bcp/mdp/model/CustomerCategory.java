package com.bcp.mdp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
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
	
}
