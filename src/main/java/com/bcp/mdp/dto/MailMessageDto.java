package com.bcp.mdp.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;

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
