package jumo.loan.builders;

import jumo.loan.pojo.LoanTuple;

public class LoanTupleBuilder {

	private String network;
	private String product;
	private String month;
	
	public LoanTuple build() {
		LoanTuple loanTuple = new LoanTuple();
		loanTuple.setNetwork(network);
		loanTuple.setMonth(month);
		loanTuple.setProduct(product);
		return loanTuple;
	}
	
	public static LoanTupleBuilder aLoanTupleBuilder() {
		return new LoanTupleBuilder();
	}
	
	public LoanTupleBuilder withNetwork(String network) {
		this.network = network;
		return this;
	}
	
	public LoanTupleBuilder withProduct(String product) {
		this.product = product;
		return this;
	}
	
	public LoanTupleBuilder withMonth(String month) {
		this.month = month;
		return this;
	}
}
