package com.bcp.mdp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class BankRegional extends Institute implements Serializable{
	
	@OneToOne
	@JsonManagedReference
	@JoinColumn(name="bcp"/* , referencedColumnName="instituteReference"*/)
	private BankCentralPop bcp;
	
	@OneToMany(mappedBy="bankRegional")
	@JsonBackReference
	private Set<Agency> agencies;
}
