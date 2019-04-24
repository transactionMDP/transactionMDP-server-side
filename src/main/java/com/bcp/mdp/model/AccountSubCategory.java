package com.bcp.mdp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
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

	public long getIdAccountSubCategory() {
		return idAccountSubCategory;
	}

	public void setIdAccountSubCategory(long idAccountSubCategory) {
		this.idAccountSubCategory = idAccountSubCategory;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public AccountCategory getAccountCategory() {
		return accountCategory;
	}

	public void setAccountCategory(AccountCategory accountCategory) {
		this.accountCategory = accountCategory;
	}

	public Set<com.bcp.mdp.model.AccountSubCategoryNature> getAccountSubCategoryNature() {
		return AccountSubCategoryNature;
	}

	public void setAccountSubCategoryNature(Set<com.bcp.mdp.model.AccountSubCategoryNature> accountSubCategoryNature) {
		AccountSubCategoryNature = accountSubCategoryNature;
	}

	public AccountSubCategory(long idAccountSubCategory, String type, AccountCategory accountCategory,
			Set<com.bcp.mdp.model.AccountSubCategoryNature> accountSubCategoryNature) {
		super();
		this.idAccountSubCategory = idAccountSubCategory;
		this.type = type;
		this.accountCategory = accountCategory;
		AccountSubCategoryNature = accountSubCategoryNature;
	}

	public AccountSubCategory() {
		super();
	}
	
	

}
