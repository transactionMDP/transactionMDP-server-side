package com.bcp.mdp.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
