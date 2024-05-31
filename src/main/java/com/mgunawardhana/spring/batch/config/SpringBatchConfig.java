/**
 * Developed By: mGunawardhana
 * Date: 29/05/2024
 * Time: 15:48
 */
package com.mgunawardhana.spring.batch.config;

import com.mgunawardhana.spring.batch.entity.Customer;
import com.mgunawardhana.spring.batch.repository.CustomerRepository;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

/**
 * {@code @Configuration} - This annotation is used to define the class as a configuration class.
 * {@code @EnableBatchProcessing} - This annotation is used to enable Spring Batch features and provides a base
 * configuration for setting up batch jobs in the application.
 */
@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
    private JobBuilder jobBuilder;
    private StepBuilder stepBuilder;
    private CustomerRepository customerRepository;

    /**
     * This method is used to create a FlatFileItemReader bean. The FlatFileItemReader reads lines from a flat file
     * and maps them to object using a LineMapper. In this case, it reads a CSV file and maps each line to a Customer
     * object.
     *
     * @return a FlatFileItemReader<Customer> bean
     */
    @Bean
    public FlatFileItemReader<Customer> flatFileItemReader() {

        FlatFileItemReader<Customer> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/customer.csv"));
        flatFileItemReader.setName("csvReader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());

        return flatFileItemReader;
    }

    /**
     * This method is used to create a LineMapper that maps lines from a CSV file to a Customer object.
     * The DefaultLineMapper and DelimitedLineTokenizer are used in this process.
     *
     * @return a LineMapper<Customer> that maps lines from a CSV file to a Customer object
     */
    private LineMapper<Customer> lineMapper() {
        DefaultLineMapper<Customer> defaultLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "firstName", "lastName", "email", "gender", "contactNo", "country", "dob");

        BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Customer.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
        return defaultLineMapper;
    }

    /**
     * This method is used to create a CustomerProcessor bean. The CustomerProcessor is a custom class
     * that contains business logic for processing Customer objects. It is typically used in the processing
     * phase of a Spring Batch job.
     *
     * @return a new instance of CustomerProcessor
     */
    @Bean
    public CustomerProcessor customerProcessor() {
        return new CustomerProcessor();
    }

    /**
     * This method is used to create a RepositoryItemWriter bean. The RepositoryItemWriter is a Spring Batch class
     * that writes items to a data repository. It uses a method on the repository to save each item.
     * In this case, it uses the 'save' method on the CustomerRepository to save each Customer object.
     *
     * @return a RepositoryItemWriter<Customer> bean that writes Customer objects to the CustomerRepository
     */
    @Bean
    public RepositoryItemWriter<Customer> repositoryItemWriter() {
        RepositoryItemWriter<Customer> repositoryItemWriter = new RepositoryItemWriter<>();

        repositoryItemWriter.setRepository(customerRepository);
        repositoryItemWriter.setMethodName("save");
        return repositoryItemWriter;
    }
}
