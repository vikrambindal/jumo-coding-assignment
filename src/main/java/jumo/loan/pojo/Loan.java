package jumo.loan.pojo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"loanAggregate"})
@ToString(exclude={"loanAggregate"})
public class Loan implements Serializable {
	
	private static final long serialVersionUID = -4967542463725232125L;
	
	private LoanTuple loanTuple;
	private double amount;
	private LoanAggregate loanAggregate;
}
