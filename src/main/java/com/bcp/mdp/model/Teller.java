package com.bcp.mdp.model;

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
