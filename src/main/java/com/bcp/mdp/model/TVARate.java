package com.bcp.mdp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class TVARate implements Serializable{
	
	@Id
	@GeneratedValue()
	long idTVARate;
	String TVARate;
	double tvaValue;
	/*
	@OneToOne
	@JsonBackReference
    TransferType transferType;*/

}
