package com.bcp.mdp.dao;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.bcp.mdp.model.Autorisation;

public interface AutorisationRepository extends JpaRepository<Autorisation, Long> {
	
	@Query("select aut from Autorisation aut where aut.reference= ?1 and aut.finalDate= ?2 and aut.owner.accountNumber= ?3")
	public Autorisation find(long autorisationNumber, LocalDate autorisationValidate, long accountNumber);

	@Query("select aut.balance from Autorisation aut where aut.reference=?1 ")
	public double findBalance(long autorisationNumber);
	
	@Query("select aut.freeBalance from Autorisation aut where aut.reference=?1 ")
	public double findFreeBalance(long autorisationNumber);
	
	@Transactional
	@Modifying
	@Query("update Autorisation aut set aut.balance=aut.balance +:amount where aut.reference= :autorisationNumber")
	public void updateBalance(@Param("autorisationNumber")long autorisationNumber, @Param("amount") double amount); // soustraire le montant d'une transaction
	// le solde doit etre mis à jour à chaque consommation. ? que faire lors de l'annulation
	//A l'annulation après l'exécution de la transaction: ajout du montant de la transaction au solde.
	
	@Transactional
	@Modifying
	@Query("update Autorisation aut set aut.AmountOfPendingTransfer=aut.AmountOfPendingTransfer +:amount where aut.reference= :autorisationNumber")
	public void updateAmountOfPendingTransfer(@Param("autorisationNumber")long autorisationNumber, @Param("amount") double amount);
	/* ce montant sera changé en fonction de l'état d'une transaction. 
	 *  A la création de la transaction: ajout du montant de la transaction à ce montant.
	 *  A l'exécution de la transaction: retrait du montant de la transaction à ce montant.
	 *  A l'annulation avant l'exécution de la transaction: ajout du montant de la transaction à ce montant.
	 */
}
