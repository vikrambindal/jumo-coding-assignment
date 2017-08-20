package jumo.loan.service;

import jumo.loan.pojo.Loan;

public interface LoanItemProcessService {

	public Loan process(Loan loan) throws Exception;
}
