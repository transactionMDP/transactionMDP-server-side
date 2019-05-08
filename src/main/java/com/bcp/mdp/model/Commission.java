package com.bcp.mdp.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class Commission implements Serializable  {
  
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long id;
	double commissionRate;
	double tvaRate;
	@Column(columnDefinition="double default 1")
	double exchangeTransferCurrencyToOther;
	@Column(columnDefinition=" double default 1")
	double exchangeTransferCurrencyToMAD;

}
