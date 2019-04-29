package com.bcp.mdp.web.controller;

import com.bcp.mdp.model.Account;
import com.bcp.mdp.model.AccountCategory;
import com.bcp.mdp.model.State;
import com.bcp.mdp.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin/*(origins = "http://localhost:3000", maxAge = 3600)*/
@RestController
@RequestMapping("/api/account")
public class AccountController {
	@Autowired 
	private IAccountService accountService;

	@PostMapping(value="/create")
	public void  addAccount(/*@Valid*/ @RequestBody Account account ) {
		accountService.createAccount(account);
		
	}

	@GetMapping("/getAccountDetail/{accountNumber}")
	public Account retrieveAccountDetail(@PathVariable(value = "accountNumber") long accountNumber) {
		
		return accountService.retrieveAccountByAccountNumber(accountNumber);
		
	}
	@PostMapping(value="/residence")
	public String retrieveAccountResidence(@RequestBody long accountNumber) {
		
		return accountService.retrieveAccountResidenceReference(accountNumber);
		
	}
	
	@GetMapping(value="/instituteAccountsOfGestion")
	public List<Long> retrieveInstituteAccounts() {
	
		return accountService.retrieveInstituteAccounts();
	
	}
	
	@GetMapping(value="/instituteAccountsOfGestionPL")
	public List<Long> retrieveInstituteAccountsPL() {
	
		return accountService.retrieveInstituteAccountsPL();
	
	}
	
	@GetMapping(value="/instituteAccountsOfGestionTVA")
	public List<Long> retrieveInstituteAccountsTVA() {
	
		return accountService.retrieveInstituteAccountsTVA();
	
	}
	
	@PostMapping(value="/AccountsOfGestionForInstituteReference")
	public List<Long> retrieveInstituteAccounts(@RequestBody String reference) {
		System.out.print(reference);
	
		return accountService.retrieveInstituteAccountsByReferenceOfInstitut(reference);
	
	}
	@PostMapping(value="/currency")
	public String retrieveAccountCurrent(@RequestBody long accountNumber) {
		return accountService.retrieveAccountCurrency(accountNumber);
		
		//sinon retourner etat du compte si compte opposé, cloturé;
	}
	@GetMapping(value="/list") 
	public List<Account> retrieveAccounts(){
		return accountService.retrieveAllAccount();
		
	}
	
	/*@PostMapping(value="/stateOrCurrency")
	public State retrieveStateOrCurrency(@RequestBody long accountNumber) {
		return accountService.retrieveAccountStateOrCurrency(accountNumber);
	}*/
	@GetMapping(value="/stateOrCurrency/{accountNumber}")
	public State retrieveStateOrCurrency(@PathVariable long accountNumber) {
		return accountService.retrieveAccountStateOrCurrency(accountNumber);
	}
	
	
	@PostMapping(value="/state") 
	public State retrieveState(@RequestBody long accountNumber) {
		return accountService.retrieveAccountState(accountNumber);
	}
	
	
	@PostMapping(value="/residences/") 
	public List<Account> retrieveResidentAccounts(@RequestBody long residentId){
		return accountService.retrieveInstituteAccounts(residentId);
		
	}
	@PostMapping(value="/category/") 
	public List<Account> retrieveCategoryAccounts(@RequestBody long accountCategoryId){
		return accountService.retrieveACategoryAccounts(accountCategoryId);
		
	}
	
	@GetMapping(value="/categories") 
	public List<AccountCategory> retrieveAccountCategories(){
		return accountService.retrieveAccountCategories();
		
	}
	
	
	@GetMapping(value="/customer") 
	public List<Account> retrieveCustomerAccounts(@PathVariable long customerId){
		return accountService.retrieveInstituteAccounts(customerId);
		
	}
	
	@PutMapping(value="/update/")
	public void updateAccount(@RequestBody long accountNumber,@Valid @RequestBody Account account ) {
		
	}
	
	@DeleteMapping(value="/delete")
	public void deleteAccount(@RequestBody long accountNumber) {
		
	}
	
	
	@PostMapping(value="/credit/amount")
	public void creditAccount(@RequestPart("accountNumber") long accountNumber,@RequestPart("amount") double amount) {
		System.out.println(accountNumber);
		accountService.creditAccount(accountNumber, amount);
	}
	
	@PostMapping(value="/debit/amount")
	public void debitAccount(@RequestPart("accountNumber") long accountNumber,@RequestPart("amount") double amount) {
		accountService.debitAccount(accountNumber, amount);
	}
	
	@PostMapping(value="/account/balance")
	public double retrieveAccountBalance(@RequestBody long accountNumber) {
		return accountService.retrieveBalanceByAccountNumber(accountNumber);
	}
	
	

}
