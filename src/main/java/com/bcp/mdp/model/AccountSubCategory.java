package com.bcp.mdp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor  @FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountSubCategory  implements Serializable {
	
	@Id
	@GeneratedValue()
	private long idAccountSubCategory;
	private String type;
	
	@OneToOne
	@JsonManagedReference
	private AccountCategory accountCategory;
	
	@OneToMany(mappedBy="accountSubCategory")
	@JsonBackReference
	private Set<com.bcp.mdp.model.AccountSubCategoryNature> AccountSubCategoryNature;


}
