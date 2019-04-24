package com.bcp.mdp.dto;

//@Component(Amount="tarif")
public class TarificationOfTransaction {
	private double commissionRate;
	private double commissionAmount;
	
	private double tvaRate;
	private double tvaAmount;
	
	private double sumAmount; // la somme de la commission et de la tva sur commission

	public TarificationOfTransaction() {
		super();
	}

	public TarificationOfTransaction(double commissionRate, double commissionAmount, double tvaRate, double tvaAmount,
			double sumAmount) {
		super();
		commissionRate = commissionRate;
		commissionAmount = commissionAmount;
		this.tvaRate = tvaRate;
		this.tvaAmount = tvaAmount;
		this.sumAmount = sumAmount;
	}

	public double getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(double commissionRate) {
		this.commissionRate = commissionRate;
	}

	public double getCommissionAmount() {
		return commissionAmount;
	}

	public void setCommissionAmount(double commissionAmount) {
		this.commissionAmount = commissionAmount;
	}

	public double getTvaRate() {
		return tvaRate;
	}

	public void setTvaRate(double tvaRate) {
		this.tvaRate = tvaRate;
	}

	public double getTvaAmount() {
		return tvaAmount;
	}

	public void setTvaAmount(double tvaAmount) {
		this.tvaAmount = tvaAmount;
	}

	public double getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(double sumAmount) {
		this.sumAmount = sumAmount;
	}

	

}
