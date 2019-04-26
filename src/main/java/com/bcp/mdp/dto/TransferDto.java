package com.bcp.mdp.dto;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class TransferDto {
	private long principalAccount;
	private long beneficiaryAccount ;
	private String transferReason;
	private Date executionDate;
	private String chargeType;
	private String transactionCurrency;
	private double commissionRate;
	private double commissionAmount;
	private double commissionTVA;
	private double transactionAmount;


}
