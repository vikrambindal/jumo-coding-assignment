package jumo.loan.pojo;

import java.io.Serializable;

import lombok.Data;

@Data
public class LoanTuple implements Serializable {

	private static final long serialVersionUID = -5044370578786962195L;
	
	private String network;
	private String product;
	private String month;
}
