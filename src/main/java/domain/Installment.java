package domain;

import java.math.BigDecimal;

public class Installment {

	private int number;
	private BigDecimal amount;
	private BigDecimal amountOfCapital;
	private BigDecimal amountOfInterest;
	private BigDecimal fixedFees;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmountOfCapital() {
		return amountOfCapital;
	}

	public void setAmountOfCapital(BigDecimal amountOfCapital) {
		this.amountOfCapital = amountOfCapital;
	}

	public BigDecimal getAmountOfInterest() {
		return amountOfInterest;
	}

	public void setAmountOfInterest(BigDecimal amountOfInterest) {
		this.amountOfInterest = amountOfInterest;
	}

	public BigDecimal getFixedFees() {
		return fixedFees;
	}

	public void setFixedFees(BigDecimal fixedFees) {
		this.fixedFees = fixedFees;
	}
}
