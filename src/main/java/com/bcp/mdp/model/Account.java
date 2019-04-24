package com.bcp.mdp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;


@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class Account implements Serializable{
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	long idAccount;
	
	@Column(name = "accountNumber", nullable=false)
	long accountNumber;
	
	Date createDate;
	/* pourquoi utiliser  java.sql.Date au lieu de java.util.Date ,parce que java.sql.Date
	 * crée automatiquement un type date avec une date par defaut qui est la date du jour au lieu d'utiliser @Temporal
	 */
	
	@Column(nullable=false)
	double balance;
	//double obligation; //sum of amount that will be debited at an execution date of a transaction
	//double freeBalance; // that is a real free balance on a account , its is balance-obligation
	
	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name="state" , referencedColumnName="code")
    State state; // definit l'état d'un compte, s'il est en activité, cloturé, gêlé, restreint au debit ou credit
	
	/*@Column(nullable=true)
	private double facilities;
	/*
	@Column(nullable=true)
	private double debit; // definit le solde debiteur;
	
	@Column(nullable=true)
	private double credit;// definit le solde crediteur;*/

	 
	
	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name="accountCategory" , referencedColumnName="type")
    AccountCategory accountCategory;
	
	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name="currency" , referencedColumnName="name")
    Currency accountCurrency;
	
	@ManyToOne 
	@JsonManagedReference
	@JoinColumn(name="resident" , referencedColumnName="instituteReference")
    Institute accountResident;
	//private String accountResident;
	
	/*@OneToMany(mappedBy="")
	private Set<Customer> accountCustomer;*/
	
	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name="customer" , referencedColumnName="customerId")
    Customer accountCustomer;
	
	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name="instituteAccount" , referencedColumnName="instituteReference")
    Institute instituteAccount;
	
	@OneToMany(mappedBy="creditAccount")
	@JsonBackReference
	Set<Transaction> transactionsCredit;
	
	
	@OneToMany(mappedBy="debitAccount")
	@JsonBackReference
	Set<Transaction> transactionsDebit;

	public long getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

}
