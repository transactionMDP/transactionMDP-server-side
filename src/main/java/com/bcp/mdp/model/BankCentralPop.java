package com.bcp.mdp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class BankCentralPop extends Institute implements Serializable {
	
	@OneToMany(mappedBy="bcp")
	@JsonBackReference
	Set<BankRegional> bprs;
}
