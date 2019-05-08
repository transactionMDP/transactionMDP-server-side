package com.bcp.mdp.model;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerParticular extends Customer implements Serializable{
	 String lastName;
	 String firstName;
	 String cin;
	 String sex;
	 Date birthDay;
	 String nationality;
	 String birthPlace;
}
