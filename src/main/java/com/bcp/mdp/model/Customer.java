package com.bcp.mdp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer implements Serializable{
	
	@Id
	@GeneratedValue
	long customerId;
	String name;
	String  address;
	String email;
	@Column(nullable=true)
	int phoneNumber;
	Date createDate;
	
	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name="state" , referencedColumnName="code")
    State state;// client inexistant, client décédé, saisie arret, atd
	
	@OneToOne
	@JsonManagedReference
    CustomerCategory customerCategory;
	
	@OneToMany(mappedBy="accountCustomer")
	@JsonBackReference
	Set<Account> accounts;
}
