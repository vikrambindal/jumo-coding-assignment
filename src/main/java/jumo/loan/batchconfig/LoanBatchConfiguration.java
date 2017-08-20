package jumo.loan.batchconfig;

import java.io.IOException;

import jumo.loan.Constants;
import jumo.loan.pojo.Loan;
import jumo.loan.service.LoanItemProcessorServiceImpl;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

@Configuration
public class LoanBatchConfiguration {

	@Autowired private JobBuilderFactory jobs;
	@Autowired private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	@StepScope
	public MultiResourceItemReader<Loan> csvFileReader(@Value("#{jobParameters[pathToFile]}") String pathToFile){
		MultiResourceItemReader<Loan> reader = new MultiResourceItemReader<Loan>();
		Resource[] resources = null;
	    ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();   
	    try {
	        resources = patternResolver.getResources(pathToFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		reader.setResources(resources);
		reader.setStrict(false);
		reader.setDelegate(flatCsvFileReader());
		return reader;
	}
	
	@Bean
	public LineMapper<Loan> csvLineMapper() {
		DefaultLineMapper<Loan> lineMapper = new DefaultLineMapper<Loan>();

		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(Constants.LOAN_CSV_DELIMETER);
		lineTokenizer.setStrict(false);

		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(new LoanCsvFieldSetMapper());

		return lineMapper;
	}
	
	@Bean
	public Job readAndProcessJob(Step step){
		return jobs.get("readAndProcessJob")
				.start(step)
				.build();
	}
	
	@Bean
	public Step readCSVAndProcessStep(ItemReader<Loan> itemReader){
		return stepBuilderFactory.get("readCSVAndProcessStep")
				.<Loan, Loan>chunk(10)
				.reader(itemReader)
				.processor(new LoanItemProcessorServiceImpl())
				.build();
	}
	
	private ResourceAwareItemReaderItemStream<? extends Loan> flatCsvFileReader() {
		FlatFileItemReader<Loan> reader = new FlatFileItemReader<Loan>();
		reader.setLinesToSkip(1);
		reader.setLineMapper(csvLineMapper());
		return reader;
	}
}
