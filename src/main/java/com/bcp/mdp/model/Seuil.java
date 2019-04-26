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
public class Seuil  implements Serializable{
	
	@Id
	@GeneratedValue()
	long idSeuil;
	double  type;
	double value; 
	
	@OneToMany(mappedBy="seuil")
	@JsonBackReference
	Set<ExchangeRate> exchangesSeuil;

}
