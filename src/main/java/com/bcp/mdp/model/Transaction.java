package com.bcp.mdp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class Transaction  implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	long idTransaction;
	

	//@Generated(value = GenerationTime.INSERT)
	//@Column(columnDefinition="VARCHAR(255) default concat ('#',idTransaction)")
	String reference; // la reference de la transaction sera une concatenation du type de source et de l'id
	@PostLoad  
	void setReference() {
		this.reference="R"+this.idTransaction;
	}
	String typeTransferSource; // pour preciser si ça provient de l'agence , du e-banking ...;
	String transferReason; //pour preciser le motif de la transaction;
	String reasonOfRefuse; //  optionnel , seullement si la transaction a été  refusé
	Date operationDate; //la date de la transaction qui est la date du jour;,
	Date executionDate; //la date  d'execution de la transaction qui est par défaut la date du jour;
	Boolean executed;// this attribute store if a transaction are excuted or no. And some transactions  of today can be executed or no if you restart the server 
	Date debitDate; // date de valeur au debit;
	Date creditDate; // date de valeur au credit;
	double amount; // montant total  de la transaction qui inclut le montant initié , la commission et la tva;
	
	
	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name="state")
    State state; // si la transaction est finalisé? en cours: en cours de validation? ou bien refus;
	
	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name="accountNumberCredit", referencedColumnName="accountNumber")
    Account creditAccount; // réprésente le compte beneficiaire;

	@ManyToOne  
	@JsonManagedReference
	@JoinColumn(name="accountNumberDedit", referencedColumnName="accountNumber")
    Account debitAccount; // réprésente le compte ordonnateur;

	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name="currency" , referencedColumnName="name")
    Currency transactionCurrency;
	
	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name="typeOfTransfert" , referencedColumnName="type")
    TransferType transactionTransferType; // pour preciser si c'est intra-agence, intra-BPR,
	
	@ManyToOne
	@JsonManagedReference
    Teller tellerInitiator;
	
	@ManyToOne
	@JsonManagedReference
    Teller tellerVerificator;

	public long getIdTransaction() {
		return idTransaction;
	}

	public void setIdTransaction(long idTransaction) {
		this.idTransaction = idTransaction;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getTypeTransferSource() {
		return typeTransferSource;
	}

	public void setTypeTransferSource(String typeTransferSource) {
		this.typeTransferSource = typeTransferSource;
	}

	public String getReasonOfRefuse() {
		return reasonOfRefuse;
	}

	public void setReasonOfRefuse(String reasonOfRefuse) {
		this.reasonOfRefuse = reasonOfRefuse;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	public Date getDebitDate() {
		return debitDate;
	}

	public void setDebitDate(Date debitDate) {
		this.debitDate = debitDate;
	}

	public Date getCreditDate() {
		return creditDate;
	}

	public void setCreditDate(Date creditDate) {
		this.creditDate = creditDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Account getCreditAccount() {
		return creditAccount;
	}

	public void setCreditAccount(Account creditAccount) {
		this.creditAccount = creditAccount;
	}

	public Account getDebitAccount() {
		return debitAccount;
	}

	public void setDebitAccount(Account debitAccount) {
		this.debitAccount = debitAccount;
	}

	public Currency getTransactionCurrency() {
		return transactionCurrency;
	}

	public void setTransactionCurrency(Currency transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}

	public TransferType getTransactionTransferType() {
		return transactionTransferType;
	}

	public void setTransactionTransferType(TransferType transactionTransferType) {
		this.transactionTransferType = transactionTransferType;
	}

	public Teller getTellerInitiator() {
		return tellerInitiator;
	}

	public void setTellerInitiator(Teller tellerInitiator) {
		this.tellerInitiator = tellerInitiator;
	}

	public Teller getTellerVerificator() {
		return tellerVerificator;
	}

	public void setTellerVerificator(Teller tellerVerificator) {
		this.tellerVerificator = tellerVerificator;
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

	public Boolean getExecuted() {
		return executed;
	}

	public void setExecuted(Boolean executed) {
		this.executed = executed;
	}
}
