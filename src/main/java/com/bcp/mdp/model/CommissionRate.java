package com.bcp.mdp.model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class CommissionRate implements Serializable{
	
	@Id 
	@GeneratedValue()
	long IdCommission;
	String nameCommission;
	double valueCommission;
	/*@OneToOne(mappedBy="commission")
	@JsonManagedReference
	TransferType transferType;*/
}
