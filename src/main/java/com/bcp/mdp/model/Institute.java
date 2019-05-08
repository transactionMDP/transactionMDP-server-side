package com.bcp.mdp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

//@MappedSuperclass
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class Institute  implements Serializable{
	
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	//@GeneratedValue
	 long idInstitute;
	 String instituteReference;
	 String nameInstitute;
	 String address;
	
	@OneToMany(mappedBy="accountResident")
	@JsonBackReference
	Set<Account> accounts;
	
	@OneToMany(mappedBy="instituteAccount")
	@JsonBackReference
	Set<Account> accountsOfGestion;
}
