package com.bcp.mdp.dto;

import javax.validation.constraints.Email;

public class MailMessageDto {
	
	@Email
	String toCreditAccount;
	
	@Email
	String toDebitAccount;
	
	String amountOfTransaction;
	
	String nameOfDebitor;
	
	String nameOfCreditor;

	public String getToCreditAccount() {
		return toCreditAccount;
	}

	public void setToCreditAccount(String toCreditAccount) {
		this.toCreditAccount = toCreditAccount;
	}

	public String getToDebitAccount() {
		return toDebitAccount;
	}

	public void setToDebitAccount(String toDebitAccount) {
		this.toDebitAccount = toDebitAccount;
	}

	public String getAmountOfTransaction() {
		return amountOfTransaction;
	}

	public void setAmountOfTransaction(String amountOfTransaction) {
		this.amountOfTransaction = amountOfTransaction;
	}

	public String getNameOfDebitor() {
		return nameOfDebitor;
	}

	public void setNameOfDebitor(String nameOfDebitor) {
		this.nameOfDebitor = nameOfDebitor;
	}

	public String getNameOfCreditor() {
		return nameOfCreditor;
	}

	public void setNameOfCreditor(String nameOfCreditor) {
		this.nameOfCreditor = nameOfCreditor;
	}
	
	
	

}
