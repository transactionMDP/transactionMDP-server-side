package com.bcp.mdp.model;

import com.bcp.mdp.model.audit.UserDateAudit;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import org.hibernate.annotations.Formula;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class Transaction  extends UserDateAudit {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	long idTransaction;
	
	@Formula("concat(source, type,id_Transaction)")
	String reference; // la reference de la transaction sera une concatenation du type de source et de l'id
	
	@Column(columnDefinition="bigint default 0")
	long autorisationNumber;

	String transferReason; //pour preciser le motif de la transaction;
	String reasonOfRefuse; //  optionnel , seullement si la transaction a été  refusé
	Date operationDate; //la date de la transaction qui est la date du jour;,
	LocalDate executionDate; //la date  d'execution de la transaction qui est par défaut la date du jour;
	Date debitDate; // date de valeur au debit;
	Date creditDate; // date de valeur au credit;
	double amount; // montant total  de la transaction qui inclut le montant initié , la commission et la tva;
	
	String chargeType;
	String transferNature;// pour la nature du tranfert: combinaison entre devise
	boolean applyCommission;
	
	// est ce que c'est la peine on peut simplement inclure ça dans la reference
	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name="type" , referencedColumnName="code")
    TransferType type; // pour preciser si c'est intra-agence, intra-BPR,
	

	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name="source", referencedColumnName="code")
	TransferSource source;    // pour preciser si ça provient de l'agence , du e-banking ...;

	
	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name="state", referencedColumnName="code")
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
	
	@OneToOne
	@JsonManagedReference
	Commission commission;

}
