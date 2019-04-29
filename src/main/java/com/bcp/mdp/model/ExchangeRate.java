package com.bcp.mdp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class ExchangeRate implements Serializable {
	
	@Id
	@GeneratedValue()
	long idExchangeRate;
	String name;
	double instantCourse;
	double batchNegociatedCourse;
	double individualNegociatedCourse;
	
	@OneToOne
	@JsonManagedReference
    Currency buyCurrency;
	
	@OneToOne
	@JsonManagedReference
    Currency sellCurrency;
	
	
	@OneToOne
	@JsonManagedReference
    Seuil seuil;
}
