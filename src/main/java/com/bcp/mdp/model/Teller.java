package com.bcp.mdp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
/*
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class Teller implements Serializable {
	
	@Id
	@GeneratedValue
	long idTeller;
	long TellerRegistrationNumber;
	String lastName;
	String firstname;
	
	@OneToOne
	@JsonManagedReference
    Agency agency;
	
	@OneToMany(mappedBy="tellerInitiator")
	@JsonBackReference
	Set<Transaction> transactionsInitiator;
	
	@OneToMany(mappedBy="tellerVerificator")
	@JsonBackReference
	Set<Transaction> transactionsVerifier;

}
*/
