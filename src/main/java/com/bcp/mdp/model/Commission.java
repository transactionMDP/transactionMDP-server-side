package com.bcp.mdp.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class Commission {
  
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long id;
	double commissionRate;
	double tvaRate;
	@Column(columnDefinition="double default 1")
	double exchangeTransferCurrencyToOther;
	@Column(columnDefinition="double default 1")
	double exchangeTransferCurrencyToMAD;
	
	@Transient
	boolean transactionCurrencyEqualsDebitCurrency;



}
