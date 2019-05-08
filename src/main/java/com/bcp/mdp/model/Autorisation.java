package com.bcp.mdp.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Formula;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class Autorisation {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	long id;
	long reference;
	double initialAmount;
	@Column(columnDefinition="double  default 0")
	double balance; // à mettee à jour à chaque consommation de l'autorisation.
	double AmountOfPendingTransfer;
	@Formula("balance-Amount_Of_Pending_Transfer")
	double freeBalance;
	LocalDate debDate;
	LocalDate finalDate;
	String motif; // doit etre renseigné à partir d'une liste déroulante.
	
	@OneToOne
	@JoinColumn(name="owner", referencedColumnName="accountNumber")
	Account owner;
	
}
