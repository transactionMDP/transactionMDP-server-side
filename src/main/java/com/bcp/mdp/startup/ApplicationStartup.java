package com.bcp.mdp.startup;

import java.io.IOException;
import java.time.LocalDate;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import com.bcp.mdp.service.ITransferInBPService;

public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private ITransferInBPService transactionService;
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		// TODO Auto-generated method stub
		try {
			transactionService.doTransactionOfExecutionDayToday(transactionService.retrieveTransactionsToExecuteToday(LocalDate.now()));
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// mettre à jour les cours instantannée d'echange
	}

}
