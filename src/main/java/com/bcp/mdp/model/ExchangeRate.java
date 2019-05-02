package com.bcp.mdp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class ExchangeRate implements Serializable {
	
	@Id
	@GeneratedValue()
	long idExchangeRate;
	//String name;
	double instantCourse;
	double batchNegociatedCourse;
	double individualNegociatedCourse;
	double preferentialCourse;

	@OneToOne
	@JsonManagedReference
	@JoinColumn(name="fromCurrency" , referencedColumnName="code")
	Currency sellCurrency;

	@OneToOne
	@JsonManagedReference
	@JoinColumn(name="toCurrency" , referencedColumnName="code")
    Currency buyCurrency;
	
	
	@OneToOne
	@JsonManagedReference
    Seuil seuil;
}
