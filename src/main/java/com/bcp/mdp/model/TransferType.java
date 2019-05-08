package com.bcp.mdp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class TransferType  implements Serializable{
	
	@Id
	@GeneratedValue()
	long idTransferType;
	String code;
	String type;
	
	@OneToOne 
	@JsonIgnore
    CommissionRate commission;
	
	@OneToOne
	@JsonIgnore
    TVARate tva;
	
	
}
