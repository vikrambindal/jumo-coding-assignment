package jumo.loan.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jumo.loan.Constants;
import jumo.loan.pojo.Loan;
import jumo.loan.pojo.LoanAggregate;
import jumo.loan.pojo.LoanTuple;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

/**
 * Purpose: This class is part of the configured spring batch where items read from a Csv file
 * 			are then processed in chunked fashion adhering to the nature of the batch configuration.
 * 			The processed records are stored in the execution context of a job as parameter for retrieval
 * @author Vikram
 */
@Service
public class LoanItemProcessorServiceImpl implements ItemProcessor<Loan, Loan>, LoanItemProcessService {

	private Map<LoanTuple, Loan> loanMap = new HashMap<>();
	private Set<Loan> loans = new HashSet<>();
	private StepExecution stepExecution;
	
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}
	
	/**
	 * Description: This method is responsible for processing the Loan record read from a Csv file
	 * 				It uses an in-memory cache (in form of a map) to store tuple and the aggregated loan information
	 * @param loan: The object extracted from a mapped row in a Csv file
	 */
	@Override
	public Loan process(Loan loan) throws Exception {
		
		double totalAmount = loan.getAmount();
		int numberOfLoans = 1;
		
		if (loanMap.containsKey(loan.getLoanTuple())) {
			totalAmount += loanMap.get(loan.getLoanTuple()).getLoanAggregate().getTotalAmount();
			numberOfLoans = loanMap.get(loan.getLoanTuple()).getLoanAggregate().getNumberOfLoans() + 1;
		}
		
		loan.setLoanAggregate(new LoanAggregate(totalAmount, numberOfLoans));
		
		loanMap.put(loan.getLoanTuple(), loan);
		loans = new HashSet<>(loanMap.values());
		
		stepExecution.getJobExecution().getExecutionContext().put(Constants.LOAN_PROCESS_KEY, loans);
		
		return loan;
	}
	
	public Set<Loan> getLoans() {
		return loans;
	}
}
