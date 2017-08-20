package jumo.loan.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoanAggregate implements Serializable {
	
	private static final long serialVersionUID = -7994756505757456687L;
	
	private double totalAmount;
	private int numberOfLoans;
}
