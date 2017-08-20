package jumo.loan.batchconfig;

import jumo.loan.pojo.Loan;
import jumo.loan.pojo.LoanTuple;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 * Purpose: This class is used to map rows read from a Csv file to a Loan object
 * @author Vikram
 */
public class LoanCsvFieldSetMapper implements FieldSetMapper<Loan> {

	/**
	 * Description: This method is used to map row of record from Csv to a Loan object
	 * @param fieldSet: FieldSet containing the row record read from Csv
	 */
	@Override
	public Loan mapFieldSet(FieldSet fieldSet) throws BindException {
		
		if (fieldSet.getFieldCount() != 5) {
			throw new BindException(fieldSet, "fieldSet");
		} else {
			LoanTuple loanTuple = new LoanTuple();
			loanTuple.setNetwork(fieldSet.readString(1).replace("'", ""));
			String date = fieldSet.readString(2).replace("'", "");
			loanTuple.setMonth(date.substring(3));
			loanTuple.setProduct(fieldSet.readString(3).replace("'", ""));
			
			Loan loan = new Loan();
			loan.setLoanTuple(loanTuple);
			loan.setAmount(fieldSet.readDouble(4));
			
			return loan;
		}
	}
}
