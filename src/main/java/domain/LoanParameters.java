package domain;

public class LoanParameters {

	private double amount;
	private int numberOfInstallments;
	private double interestRate;
	private double fee;
	private boolean constant;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getNumberOfInstallments() {
		return numberOfInstallments;
	}

	public void setNumberOfInstallments(int number) {
		this.numberOfInstallments = number;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public boolean isConstant() {
		return constant;
	}

	public void setConstant(boolean constant) {
		this.constant = constant;
	}
}
