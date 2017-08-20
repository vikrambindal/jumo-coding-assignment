package jumo.loan.builders;

import jumo.loan.pojo.Loan;
import jumo.loan.pojo.LoanAggregate;
import jumo.loan.pojo.LoanTuple;

public class LoanBuilder {

	private LoanTuple loanTuple;
	private double amount;
	private LoanAggregate loanAggregate;
	
	public Loan build() {
		Loan loan = new Loan();
		loan.setAmount(amount);
		loan.setLoanAggregate(loanAggregate);
		loan.setLoanTuple(loanTuple);
		return loan;
	}
	
	public static LoanBuilder aLoanBuilder() {
		return new LoanBuilder();
	}
	
	public LoanBuilder withLoanTuple(LoanTuple loanTuple) {
		this.loanTuple = loanTuple;
		return this;
	}
	
	public LoanBuilder withAmount(double amount) {
		this.amount = amount;
		return this;
	}
	
	public LoanBuilder withLoanAggregate(LoanAggregate loanAggregate) {
		this.loanAggregate = loanAggregate;
		return this;
	}
}
