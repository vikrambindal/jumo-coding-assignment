package jumo.loan.service;

import java.util.Collection;

import jumo.loan.pojo.Loan;

public interface LoanCsvWriteService {

	public void writeToCsv(Collection<Loan> loans);
}
