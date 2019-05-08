package com.bcp.mdp.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class ValorisationRequest {

	long accountDebit;
	String transactionCurrency;  
	double amount ;
	double commissionAmount;
}
