package com.bcp.mdp.dao;

import com.bcp.mdp.model.TransferType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransferTypeDao extends JpaRepository<TransferType, Long> {

	@Query("select t from TransferType t where t.type= :type")
	public TransferType findByType(@Param("type") String type);
	
	@Query("select t.commission.valueCommission from TransferType t  where t= :type")
	public double findTransferTypeCommission(@Param("type") TransferType type);
	

	@Query("select t.tva.tvaValue from TransferType t where t= :type")
	public double findTransferTypeTVA(@Param("type") TransferType type);
}
