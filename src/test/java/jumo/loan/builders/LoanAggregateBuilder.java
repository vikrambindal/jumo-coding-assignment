package jumo.loan.builders;

import jumo.loan.pojo.LoanAggregate;

public class LoanAggregateBuilder {

	private double totalAmount;
	private int numberOfLoans;
	
	public LoanAggregate build() {
		LoanAggregate loanAggregate = new LoanAggregate();
		loanAggregate.setNumberOfLoans(numberOfLoans);
		loanAggregate.setTotalAmount(totalAmount);
		return loanAggregate;
	}
	
	public static LoanAggregateBuilder aLoanAggregateBuilder() {
		return new LoanAggregateBuilder();
	}
	
	public LoanAggregateBuilder withTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
		return this;
	}
	
	public LoanAggregateBuilder withNumberOfLoans(int numberOfLoans) {
		this.numberOfLoans = numberOfLoans;
		return this;
	}
}
