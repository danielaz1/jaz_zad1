package services;

import domain.Installment;
import domain.LoanParameters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;

public class LoanService {

	public List<Installment> getInstallments(LoanParameters parameters) {
		return parameters.isConstant() ? getConstantInstallments(parameters) : getDecreasingInstallments(parameters);
	}

	private List<Installment> getDecreasingInstallments(LoanParameters parameters) {
		List<Installment> installments = new ArrayList<>();

		BigDecimal totalAmount = BigDecimal.valueOf(parameters.getAmount());

		double q = parameters.getInterestRate() / 12;
		int n = parameters.getNumberOfInstallments();

		BigDecimal fixedFees = BigDecimal.valueOf(parameters.getFee() / parameters.getNumberOfInstallments());
		BigDecimal totalFixedFees = BigDecimal.valueOf(parameters.getFee());

		for (int i = 0; i < parameters.getNumberOfInstallments(); i++) {
			BigDecimal amountOfInterest = totalAmount.add(totalFixedFees).multiply(BigDecimal.valueOf(q));
			BigDecimal amountOfCapital = totalAmount.divide(BigDecimal.valueOf(n - i), BigDecimal.ROUND_HALF_UP);
			totalAmount = totalAmount.subtract(amountOfCapital);
			BigDecimal amount = amountOfCapital.add(amountOfInterest);
			totalFixedFees = totalFixedFees.subtract(fixedFees);

			Installment installment = new Installment();
			installment.setNumber(i + 1);
			installment.setAmount(amount);
			installment.setAmountOfCapital(amountOfCapital);
			installment.setAmountOfInterest(amountOfInterest);
			installment.setFixedFees(fixedFees);
			installments.add(installment);
		}
		return installments;
	}

	private List<Installment> getConstantInstallments(LoanParameters parameters) {
		List<Installment> installments = new ArrayList<>();

		double totalAmount = parameters.getAmount() + parameters.getFee();
		double q = 1 + (parameters.getInterestRate() / 12);
		int n = parameters.getNumberOfInstallments();

		BigDecimal amount = BigDecimal.valueOf(totalAmount * (pow(q, n)) * ((q - 1) / (pow(q, n) - 1)));
		BigDecimal amountOfCapital = BigDecimal.valueOf(parameters.getAmount() / parameters.getNumberOfInstallments());
		BigDecimal fixedFees = BigDecimal.valueOf(parameters.getFee() / parameters.getNumberOfInstallments());
		BigDecimal amountOfInterest = amount.subtract(amountOfCapital).subtract(fixedFees);

		for (int i = 0; i < parameters.getNumberOfInstallments(); i++) {
			Installment installment = new Installment();
			installment.setNumber(i + 1);
			installment.setAmount(amount);
			installment.setAmountOfCapital(amountOfCapital);
			installment.setAmountOfInterest(amountOfInterest);
			installment.setFixedFees(fixedFees);
			installments.add(installment);
		}
		return installments;
	}
}
