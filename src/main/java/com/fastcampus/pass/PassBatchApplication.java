package com.fastcampus.pass;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@SpringBootApplication

/**
 * //@EnableBatchProcessing
from https://www.baeldung.com/spring-boot-spring-batch
First, we’ll start with a standard Spring @Configuration class. Note that with Spring boot 3.0, the @EnableBatchProcessing is discouraged. Also, JobBuilderFactory and StepBuilderFactory are deprecated and it is recommended to use JobBuilder and StepBuilder classes with the name of the job or step builder.
*/

public class PassBatchApplication {

    @Bean
	public Step passStep(JobRepository jobRepository) {
		PlatformTransactionManager platformTransactionManager = new ResourcelessTransactionManager();
		return new StepBuilder("passStep", jobRepository)
				.tasklet((param1, param2) -> {
					System.out.println("passStep");
					return RepeatStatus.FINISHED;
				}, platformTransactionManager)
				.build();
	}

	@Bean
	public Job passJob(JobRepository jobRepository) {
		return new JobBuilder("passJob", jobRepository)
				.start(passStep(jobRepository))
				.build();
	}




    public static void main(String[] args) {
		SpringApplication.run(PassBatchApplication.class, args);
	}

}
