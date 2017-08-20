package jumo.loan.service;

import java.util.Collection;

import jumo.loan.pojo.Loan;

public interface LoanBatchProcessService {

	public Collection<Loan> batchReadAndProcessLoans();
}
