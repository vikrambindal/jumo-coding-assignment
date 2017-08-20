package jumo.loan;

import java.util.Collection;

import jumo.loan.pojo.Loan;
import jumo.loan.service.LoanBatchProcessService;
import jumo.loan.service.LoanCsvWriteService;

import org.apache.log4j.Logger;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableBatchProcessing
public class Application {

	private static Logger log = Logger.getLogger(Application.class.getName());
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		
		LoanBatchProcessService loanBatchProcessService = context.getBean(LoanBatchProcessService.class);
		LoanCsvWriteService loanCSVWriteService = context.getBean(LoanCsvWriteService.class);
		
		log.info("Batch processing CSV file...");
		
		Collection<Loan> loans = loanBatchProcessService.batchReadAndProcessLoans();
		loanCSVWriteService.writeToCsv(loans);
		
		log.info("Finished Processing Loans, result in Output.csv");
	}
}
