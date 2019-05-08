package com.bcp.mdp.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class TransferRequest {
	 String transactionSource;
	 String transferNature;
	 long autorisationNumber;
	 LocalDate  autorisationValidate;
	 long principalAccount;
	 long beneficiaryAccount ;
	 String transferReason;
	 LocalDate executionDate;
	 String chargeType;
	 String transactionCurrency;
	 boolean applyCommission;
	 double commissionRate;
	 double commissionAmount;
	 double TVARate;
	 double commissionTVA;
	 double transactionAmount;
}
