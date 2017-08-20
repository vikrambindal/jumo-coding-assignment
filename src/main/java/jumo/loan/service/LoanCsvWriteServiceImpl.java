package jumo.loan.service;

import java.io.FileWriter;
import java.util.Collection;

import jumo.loan.Constants;
import jumo.loan.pojo.Loan;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

/**
 * Purpose: This class is responsible for writing records to a CSV File.
 * @author Vikram
 */
@Service
public class LoanCsvWriteServiceImpl implements LoanCsvWriteService {

	/**
	 * Description: This method is responsible for taking collection of loans and writing them to CSV File.
	 * @param loans: Collection containing the processed loans with aggregated information
	 */
	@Override
	public void writeToCsv(Collection<Loan> loans) {
		try(CSVPrinter csvFilePrinter = new CSVPrinter(new FileWriter("Output.csv"), 
				CSVFormat.DEFAULT.withRecordSeparator(Constants.NEW_LINE_SEPARATOR));) {

			csvFilePrinter.printRecord(Constants.FILE_HEADER);
			
			for(Loan loan: loans) {
				csvFilePrinter.printRecord(loan.getLoanTuple().getNetwork(), 
						loan.getLoanTuple().getProduct(), 
						loan.getLoanTuple().getMonth(), 
						loan.getLoanAggregate().getTotalAmount(), 
						loan.getLoanAggregate().getNumberOfLoans());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
