package com.bcp.mdp.dto;

import java.sql.Date;
import java.util.Set;

import javax.validation.constraints.Email;

import com.bcp.mdp.model.AccountCategory;
import com.bcp.mdp.model.Currency;
import com.bcp.mdp.model.Customer;
import com.bcp.mdp.model.Institute;
import com.bcp.mdp.model.State;
import com.bcp.mdp.model.Transaction;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class MailMessageDto {
	
	@Email
	String toCreditAccount;
	
	@Email
	String toDebitAccount;
	
	double amountOfTransaction;
	
	String nameOfDebitor;
	
	String nameOfCreditor;

}
