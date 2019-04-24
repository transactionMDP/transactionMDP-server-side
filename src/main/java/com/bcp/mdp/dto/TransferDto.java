package com.bcp.mdp.dto;

import java.util.Date;

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
	public TransferDto() {
		super();
	}
	public TransferDto(long principalAccount, long beneficiaryAccount, String transferReason, Date executionDate,
			String chargeType, String transactionCurrency, double commissionRate, double commissionAmount,
			double commissionTVA, double amount) {
		super();
		this.principalAccount = principalAccount;
		this.beneficiaryAccount = beneficiaryAccount;
		this.transferReason = transferReason;
		this.executionDate = executionDate;
		this.chargeType = chargeType;
		this.transactionCurrency = transactionCurrency;
		this.commissionRate = commissionRate;
		this.commissionAmount = commissionAmount;
		this.commissionTVA = commissionTVA;
		this.transactionAmount = amount;
	}
	public long getPrincipalAccount() {
		return principalAccount;
	}
	public void setPrincipalAccount(long principalAccount) {
		this.principalAccount = principalAccount;
	}
	public long getBeneficiaryAccount() {
		return beneficiaryAccount;
	}
	public void setBeneficiaryAccount(long beneficiaryAccount) {
		this.beneficiaryAccount = beneficiaryAccount;
	}
	public String getTransferReason() {
		return transferReason;
	}
	public void setTransferReason(String transferReason) {
		this.transferReason = transferReason;
	}
	public Date getExecutionDate() {
		return executionDate;
	}
	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}
	public String getChargeType() {
		return chargeType;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public String getTransactionCurrency() {
		return transactionCurrency;
	}
	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}
	public double getCommissionRate() {
		return commissionRate;
	}
	public void setCommissionRate(double commissionRate) {
		this.commissionRate = commissionRate;
	}
	public double getCommissionAmount() {
		return commissionAmount;
	}
	public void setCommissionAmount(double commissionAmount) {
		this.commissionAmount = commissionAmount;
	}
	public double getCommissionTVA() {
		return commissionTVA;
	}
	public void setCommissionTVA(double commissionTVA) {
		this.commissionTVA = commissionTVA;
	}
	public double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	
	

}
