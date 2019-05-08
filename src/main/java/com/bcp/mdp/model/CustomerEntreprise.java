package com.bcp.mdp.model;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerEntreprise extends Customer implements Serializable {
	String type;
	
	
}
