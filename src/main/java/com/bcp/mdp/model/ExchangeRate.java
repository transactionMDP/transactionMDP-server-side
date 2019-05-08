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
	@Column(columnDefinition="double default 1")
	double instantCourse;
	@Column(columnDefinition="double default 1")
	double batchNegociatedCourse;
	@Column(columnDefinition="double default 1")
	double individualNegociatedCourse;
	@Column(columnDefinition="double default 1")
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
