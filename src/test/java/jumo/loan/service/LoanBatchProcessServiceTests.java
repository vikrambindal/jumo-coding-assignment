package jumo.loan.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import jumo.loan.Constants;
import jumo.loan.builders.LoanAggregateBuilder;
import jumo.loan.builders.LoanBuilder;
import jumo.loan.builders.LoanTupleBuilder;
import jumo.loan.pojo.Loan;
import jumo.loan.pojo.LoanTuple;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LoanBatchProcessServiceTests {

	public static final double IRRELEVANT_AMOUNT = 50d;
	public static final Long IRRELEVANT_JOB_ID = 1L;
	public static final String IRRELEVANT_MONTH = "irrelevant_month_one";
	public static final String IRRELEVANT_NETWORK = "irrelevant_network";
	public static final String IRRELEVANT_PRODUCT = "irrelevant_product";
	public static final String IRRELEVANT_FILE = "loans/*.csv";
	
	private LoanBatchProcessServiceImpl loanBatchProcessService;
	@Mock private JobLauncher jobLauncher;
	@Mock private Job job;
	@Mock private JobExecution jobExecution;
	@Mock private ExecutionContext executionContext;
	
	@Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@Test
	public void batchReadAndProcessLoansWithConfiguredJob_shouldLaunchBatchJobForCsvReadAndReturnProcessedLoans() throws Exception {
		//Setup
		setUp();
		
		JobParameters jobParameters = new JobParametersBuilder()
							.addString("pathToFile", IRRELEVANT_FILE)
							.toJobParameters();
		
		LoanTuple loanTuple = LoanTupleBuilder.aLoanTupleBuilder()
				.withMonth(IRRELEVANT_MONTH)
				.withNetwork(IRRELEVANT_NETWORK)
				.withProduct(IRRELEVANT_PRODUCT)
				.build();
		
		//Expected Result
		Loan expectedLoan = LoanBuilder.aLoanBuilder()
				.withAmount(IRRELEVANT_AMOUNT)
				.withLoanTuple(loanTuple)
				.withLoanAggregate(LoanAggregateBuilder.aLoanAggregateBuilder()
						.withNumberOfLoans(1)
						.withTotalAmount(IRRELEVANT_AMOUNT)
						.build())
				.build();
		
		Set<Loan> loans = new HashSet<>(Arrays.asList(expectedLoan));
		
		Mockito.when(jobLauncher.run(job, jobParameters)).thenReturn(jobExecution);
		Mockito.when(jobExecution.getExecutionContext()).thenReturn(executionContext);
		Mockito.when(executionContext.get(Constants.LOAN_PROCESS_KEY)).thenReturn(loans);
		
		Set<Loan> actualLoans = loanBatchProcessService.batchReadAndProcessLoans();
		
		assertThat(actualLoans.size(), is(equalTo(1)));
		assertThat(actualLoans.contains(expectedLoan), is(equalTo(true)));
	}
	
	public void setUp() {
		
		loanBatchProcessService = new LoanBatchProcessServiceImpl(jobLauncher, job);
	}
}
