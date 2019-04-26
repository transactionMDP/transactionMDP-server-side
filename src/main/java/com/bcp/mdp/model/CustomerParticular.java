package com.bcp.mdp.model;



import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

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
