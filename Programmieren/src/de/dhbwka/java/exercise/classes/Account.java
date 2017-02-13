package de.dhbwka.java.exercise.classes;

public class Account {

	private int accountNumber;
	private String accountOwner;
	private double bankBalance;
	private double limit;

	public Account(int accountNumber, String accountOwner, double bankBalance, double limit) {
		this.accountNumber = accountNumber;
		this.accountOwner = accountOwner;
		this.bankBalance = bankBalance;
		this.limit = -limit;
	}

	public Account(int accountNumber, String accountOwner) {
		this.accountNumber = accountNumber;
		this.accountOwner = accountOwner;
		this.bankBalance = 0;
		this.limit = -1000;
	}

	public void processDeposit(double deposit) {
		if(deposit<=0){
			System.err.println("Your deposit must be positive!");
			return;
		}
		bankBalance += deposit;
		System.out.println(String.format("Deposit[%6.2f]: new bank balance: %6.2f", deposit, bankBalance));
	}

	public void processPayment(double payment) {
		if(payment<=0){
			System.err.println("Your payment must be positive!");
			return;
		}
		if (bankBalance - payment >= limit) {
			bankBalance -= payment;
			System.out.println(String.format("Payment[%6.2f]: new bank balance: %6.2f", payment, bankBalance));
		} else {
			System.err.println(String.format("Payment of %6.2f ct would exceed the limit of %6.2f ct! Your bank balance: %6.2f",payment,
					limit, bankBalance));
		}
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public String getAccountOwner() {
		return accountOwner;
	}

	public double getBankBalance() {
		return bankBalance;
	}

	public double getLimit() {
		return limit;
	}

	public String toString() {
		return String.format("Account Number: %1d (%s); Bank Balance: %6.2f ct; Limit %6.2f ct", accountNumber, accountOwner,
				bankBalance, limit);
	}

}
