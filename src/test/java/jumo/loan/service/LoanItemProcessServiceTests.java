package jumo.loan.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Set;

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
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LoanItemProcessServiceTests {

	public static final double IRRELEVANT_AMOUNT = 50d;
	public static final String IRRELEVANT_MONTH = "irrelevant_month_one";
	public static final String IRRELEVANT_NETWORK = "irrelevant_network";
	public static final String IRRELEVANT_PRODUCT = "irrelevant_product";
	public static final String IRRELEVANT_NETWORK_TWO = "irrelevant_network_two";
	public static final String IRRELEVANT_PRODUCT_TWO = "irrelevant_product_two";
	
	private LoanItemProcessorServiceImpl loanItemProcessorService;
	@Mock private StepExecution stepExecution;
	@Mock private JobExecution jobExecution;
	@Mock private ExecutionContext executionContext;
	
	@Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@Test
	public void testProcessLoanWithNewEntry_shouldProcessLoanAndCreateNewAggregateWithSpecifiedAmountAndCountOfOne() throws Exception {
		//Setup
		setUp();
		
		LoanTuple loanTuple = LoanTupleBuilder.aLoanTupleBuilder()
				.withMonth(IRRELEVANT_MONTH)
				.withNetwork(IRRELEVANT_NETWORK)
				.withProduct(IRRELEVANT_PRODUCT)
				.build();
		
		Loan loan_one = LoanBuilder.aLoanBuilder()
				.withAmount(IRRELEVANT_AMOUNT)
				.withLoanTuple(loanTuple)
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
		
		Mockito.when(stepExecution.getJobExecution()).thenReturn(jobExecution);
		Mockito.when(jobExecution.getExecutionContext()).thenReturn(executionContext);
		
		//SUT
		loanItemProcessorService.process(loan_one);

		//Actual Result
		Set<Loan> loans = loanItemProcessorService.getLoans();
		
		assertThat(loans.size(), is(equalTo(1)));
		assertThat(loans.iterator().next(), is(equalTo(expectedLoan)));
		matchLoan(loans.iterator().next(), expectedLoan);
	}
	
	@Test
	public void testProcessLoanWithExistingTuple_shouldProcessLoanAndAggregateExistingLoanWithSpecifiedAmountAndCountOfTwo() throws Exception {
		//Setup
		setUp();
		
		LoanTuple loanTuple = LoanTupleBuilder.aLoanTupleBuilder()
				.withMonth(IRRELEVANT_MONTH)
				.withNetwork(IRRELEVANT_NETWORK)
				.withProduct(IRRELEVANT_PRODUCT)
				.build();
		
		Loan loan_one = LoanBuilder.aLoanBuilder()
				.withAmount(IRRELEVANT_AMOUNT)
				.withLoanTuple(loanTuple)
				.build();
		Loan loan_two = LoanBuilder.aLoanBuilder()
				.withAmount(IRRELEVANT_AMOUNT)
				.withLoanTuple(loanTuple)
				.build();
		
		//Expected Result
		Loan expectedLoan = LoanBuilder.aLoanBuilder()
				.withAmount(IRRELEVANT_AMOUNT)
				.withLoanTuple(loanTuple)
				.withLoanAggregate(LoanAggregateBuilder.aLoanAggregateBuilder()
						.withNumberOfLoans(2)
						.withTotalAmount(2*IRRELEVANT_AMOUNT)
						.build())
				.build();
		
		Mockito.when(stepExecution.getJobExecution()).thenReturn(jobExecution);
		Mockito.when(jobExecution.getExecutionContext()).thenReturn(executionContext);
		
		//SUT
		loanItemProcessorService.process(loan_one);
		loanItemProcessorService.process(loan_two);
		
		//Actual Result
		Set<Loan> loans = loanItemProcessorService.getLoans();
		
		assertThat(loans.size(), is(equalTo(1)));
		assertThat(loans.iterator().next(), is(equalTo(expectedLoan)));
		matchLoan(loans.iterator().next(), expectedLoan);
	}
	
	@Test
	public void testProcessLoanWithDifferentLoans_shouldProcessLoanAndAggregateGivingTwoResultsForIndividualLoans() throws Exception {
		//Setup
		setUp();
		
		LoanTuple loanTuple_one = LoanTupleBuilder.aLoanTupleBuilder()
				.withMonth(IRRELEVANT_MONTH)
				.withNetwork(IRRELEVANT_NETWORK)
				.withProduct(IRRELEVANT_PRODUCT)
				.build();
		
		LoanTuple loanTuple_two = LoanTupleBuilder.aLoanTupleBuilder()
				.withMonth(IRRELEVANT_MONTH)
				.withNetwork(IRRELEVANT_NETWORK_TWO)
				.withProduct(IRRELEVANT_PRODUCT_TWO)
				.build();
		
		Loan loan_one = LoanBuilder.aLoanBuilder()
				.withAmount(IRRELEVANT_AMOUNT)
				.withLoanTuple(loanTuple_one)
				.build();
		Loan loan_two = LoanBuilder.aLoanBuilder()
				.withAmount(IRRELEVANT_AMOUNT)
				.withLoanTuple(loanTuple_two)
				.build();
		
		//Expected Result
		Loan expectedLoan_one = LoanBuilder.aLoanBuilder()
				.withAmount(IRRELEVANT_AMOUNT)
				.withLoanTuple(loanTuple_one)
				.withLoanAggregate(LoanAggregateBuilder.aLoanAggregateBuilder()
						.withNumberOfLoans(1)
						.withTotalAmount(IRRELEVANT_AMOUNT)
						.build())
				.build();
		
		Loan expectedLoan_two = LoanBuilder.aLoanBuilder()
				.withAmount(IRRELEVANT_AMOUNT)
				.withLoanTuple(loanTuple_two)
				.withLoanAggregate(LoanAggregateBuilder.aLoanAggregateBuilder()
						.withNumberOfLoans(1)
						.withTotalAmount(IRRELEVANT_AMOUNT)
						.build())
				.build();
		
		Mockito.when(stepExecution.getJobExecution()).thenReturn(jobExecution);
		Mockito.when(jobExecution.getExecutionContext()).thenReturn(executionContext);
		
		//SUT
		loanItemProcessorService.process(loan_one);
		loanItemProcessorService.process(loan_two);
		
		//Actual Result
		Set<Loan> loans = loanItemProcessorService.getLoans();
		
		assertThat(loans.size(), is(equalTo(2)));
		assertThat(loans.contains(expectedLoan_one), is(equalTo(true)));
		assertThat(loans.contains(expectedLoan_two), is(equalTo(true)));
	}
	
	private void matchLoan(Loan actualLoan, Loan expectedLoan) {
		assertThat(actualLoan.getLoanTuple().getMonth(), 
				is(equalTo(expectedLoan.getLoanTuple().getMonth())));
		assertThat(actualLoan.getLoanTuple().getNetwork(), 
				is(equalTo(expectedLoan.getLoanTuple().getNetwork())));
		assertThat(actualLoan.getLoanTuple().getProduct(), 
				is(equalTo(expectedLoan.getLoanTuple().getProduct())));
		assertThat(actualLoan.getLoanAggregate().getNumberOfLoans(), 
				is(equalTo(expectedLoan.getLoanAggregate().getNumberOfLoans())));
		assertThat(actualLoan.getLoanAggregate().getTotalAmount(), 
				is(equalTo(expectedLoan.getLoanAggregate().getTotalAmount())));
	}
	
	public void setUp() {
		
		loanItemProcessorService = new LoanItemProcessorServiceImpl();
		
		loanItemProcessorService.beforeStep(stepExecution);
	}
}
