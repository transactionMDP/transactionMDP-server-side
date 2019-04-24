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
public class Currency implements Serializable {
	
	@Id
	@GeneratedValue()
	 long IdCurrency;
	 String name;
	
	@OneToMany(mappedBy="buyCurrency")
	@JsonBackReference
	 Set<ExchangeRate> buyExchange;
	
	@OneToMany(mappedBy="sellCurrency")
	@JsonBackReference
	Set<ExchangeRate> sellExchange;
	
	@OneToMany(mappedBy="accountCurrency")
	@JsonBackReference
	Set<Account> accounts;
	
	@OneToMany(mappedBy="transactionCurrency")
	@JsonBackReference
	Set<Transaction> transactions;

}
