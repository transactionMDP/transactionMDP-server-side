package com.bcp.mdp.model;

public class Commission {
    private long accountN1;
    private long  accountN2;
    private double amount;

    public Commission(long accountN1, long accountN2, double amount) {
        this.accountN1 = accountN1;
        this.accountN2 = accountN2;
        this.amount = amount;
    }

    public long getAccountN1() {
        return accountN1;
    }

    public void setAccountN1(long accountN1) {
        this.accountN1 = accountN1;
    }

    public long getAccountN2() {
        return accountN2;
    }

    public void setAccountN2(long accountN2) {
        this.accountN2 = accountN2;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
