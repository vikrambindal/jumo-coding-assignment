package jumo.loan;

import jumo.loan.service.LoanBatchProcessServiceTests;
import jumo.loan.service.LoanItemProcessServiceTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
   LoanBatchProcessServiceTests.class,
   LoanItemProcessServiceTests.class
})
@SpringBootTest
public class ApplicationTestSuite {
}
