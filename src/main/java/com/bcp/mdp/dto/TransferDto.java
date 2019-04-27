package com.bcp.mdp.dto;

import java.time.LocalDate;
import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class TransferDto {
	 long principalAccount;
	 long beneficiaryAccount ;
	 String transferReason;
	 LocalDate executionDate;
	 String chargeType;
	 String transactionCurrency;
	 double commissionRate;
	 double commissionAmount;
	 double commissionTVA;
	 double transactionAmount;


}
