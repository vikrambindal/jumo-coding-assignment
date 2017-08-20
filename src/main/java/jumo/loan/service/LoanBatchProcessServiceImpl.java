package jumo.loan.service;

import java.util.Set;

import jumo.loan.Constants;
import jumo.loan.pojo.Loan;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Purpose: This class is responsible for processing the reading of loan data from CSV in batches
 * 			using Spring Batch through configured jobs (@see LoanBatchConfiguration). The construct
 * 			composes of Multiple Csv files read from a location using Spring batch which are then
 * 			processed for aggregate results.  
 * @author Vikram
 */
@Service
public class LoanBatchProcessServiceImpl implements LoanBatchProcessService {

	private JobLauncher jobLauncher;
	private Job job;

	@Autowired
	public LoanBatchProcessServiceImpl(JobLauncher jobLauncher, Job job) {
		this.job = job;
		this.jobLauncher = jobLauncher;
	}
	
	/**
	 * Description: This method kick starts the batch operation for reading the loans from Csv
	 * 				and using ItemProcessor (@see LoanItemProcessorService) for loan aggregation
	 * @return:		Collection containing the processed loans that contain aggregated information
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Set<Loan> batchReadAndProcessLoans() {
		Set<Loan> loans = null;
		try {
			JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
			jobParametersBuilder.addString("pathToFile", "/loans/*.csv");

			JobExecution execution = jobLauncher.run(job, jobParametersBuilder.toJobParameters());

			loans = (Set<Loan>) execution.getExecutionContext().get(Constants.LOAN_PROCESS_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return loans;
	}
}

