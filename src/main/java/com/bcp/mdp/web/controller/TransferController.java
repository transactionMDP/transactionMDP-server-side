package com.bcp.mdp.web.controller;

import com.bcp.mdp.dto.TransferDto;
import com.bcp.mdp.model.Transaction;
import com.bcp.mdp.service.ITransferInBPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/transfer")

public class TransferController {

	@Autowired
	private ITransferInBPService transferService;
	
	@PostMapping("/dotransfer")
	public void doTransfer(@RequestBody TransferDto transfer) {
		transferService.doTransfer(transfer);
	}
	
	@GetMapping("/list")
	public List<Transaction> listTransactions() {
		return transferService.retrieveTransfers();
		
	}
	
	@GetMapping("/{reference}")
	public Transaction transactionByReference(@PathVariable String reference) {
		return transferService.retrieveByReference(reference);
		
	}
	@GetMapping("/transactionsToExecuteToday")
	public List<Transaction> retrieveTransactionsToExecuteToday(){
		return null; // transferService.retrieveTransactionsToExecuteToday();
		
	}
	@PutMapping("/cancel/{reference}")
	public void cancelTransaction(@PathVariable String reference ) {
		transferService.updateTransactionState(reference, "5000");
	}
	
	@PutMapping("/refuse/{reference}")
	public void refuseTransaction(@PathVariable String reference) {
		transferService.updateTransactionState(reference, "4000");
	}
	
	@PutMapping("/validate/{reference}")
	public void validateTransaction(@PathVariable String reference ) {
		transferService.updateTransactionState(reference, "3000");
	}
	
	@PutMapping("/sendToCtn/{reference}")
	public void updateTransactionState(@PathVariable String reference ) {
		transferService.updateTransactionState(reference, "2000");
	}
	//HJKLM%
}
