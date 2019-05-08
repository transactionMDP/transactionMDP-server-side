package com.bcp.mdp.web.controller;

import com.bcp.mdp.dto.TransferDto;
import com.bcp.mdp.exception.AppException;
import com.bcp.mdp.model.Transaction;
import com.bcp.mdp.security.CurrentUser;
import com.bcp.mdp.security.UserPrincipal;
import com.bcp.mdp.service.ITransferInBPService;
import com.bcp.mdp.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/transfer")

public class TransferController {

	@Autowired
	private ITransferInBPService transferService;
	
	@PostMapping("/dotransfer")
	public String /*ResponseEntity<?>*/ doTransfer(@CurrentUser UserPrincipal currentUser, @RequestBody TransferDto transfer) {
		return transferService.doTransfer(currentUser,transfer);
	}
	
	@GetMapping("/list")
	public List<Transaction> listTransactions() {
		return transferService.retrieveTransfers();
		
	}
	
	@GetMapping("/listToExecute")
	public List<Transaction> list() {
		return transferService.retrieveTransactionsToExecuteToday(LocalDate.now());
		
	}

	@GetMapping("/me")
	public List<Transaction>/*PagedResponse<Transaction>*/ getUserTransfers(@CurrentUser UserPrincipal currentUser,
                                                                            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                                            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
		return transferService.getUserTransfers(currentUser.getUsername(), page, size);
	}

	@GetMapping("/ctrllist")
	public List<Transaction>/*PagedResponse<Transaction>*/ getCTRLTransfers(@CurrentUser UserPrincipal currentUser,
                                                                            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                                            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
		return transferService.getTransfersByState("1000", page, size);
	}

	@GetMapping("/ctnlist")
	public List<Transaction>/*PagedResponse<Transaction>*/ getCTNTransfers(@CurrentUser UserPrincipal currentUser,
                                                                           @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                                           @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
		return transferService.getTransfersByState("2000", page, size);
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
	
}
