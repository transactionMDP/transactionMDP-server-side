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
public class AccountSubCategoryNature  implements Serializable {
	
	@Id
	@GeneratedValue()
	private long idAccountSubCategoryNature;
	private String type;
	
	@OneToOne
	@JsonManagedReference
	private AccountSubCategory accountSubCategory;

}
