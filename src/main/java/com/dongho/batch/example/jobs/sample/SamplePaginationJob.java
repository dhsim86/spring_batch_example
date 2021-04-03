package com.dongho.batch.example.jobs.sample;

import com.dongho.batch.example.domain.Element;
import com.dongho.batch.example.domain.Item;
import com.dongho.batch.example.domain.ItemService;
import com.dongho.batch.example.jobs.sample.step.SampleItemPreProcessTasklet;
import com.dongho.batch.example.jobs.sample.step.SampleItemProcessor;
import com.dongho.batch.example.jobs.sample.step.SampleItemWriter;
import com.dongho.batch.example.jobs.sample.step.SamplePagingItemReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableBatchProcessing
public class SamplePaginationJob {

    private static final String JOB_NAME = "SamplePaginationJob";

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ItemService itemService;

    @Bean(name = JOB_NAME)
    public Job samplePaginationJob() {
        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .listener(new SamplePaginationListener(itemService))
                .start(sampleItemPreProcessStep())
                .next(samplePaginationDecider())
                .from(samplePaginationDecider())
                    .on(RepeatStatus.CONTINUABLE.name())
                    .to(sampleItemProcessStep())
                    .next(sampleItemPreProcessStep())
                .from(samplePaginationDecider())
                    .on("*")
                    .end()
                .end()
                .build();
    }

    @Bean
    public Step sampleItemPreProcessStep() {
        Tasklet sampleItemPreProcessTasklet = new SampleItemPreProcessTasklet();

        return stepBuilderFactory.get("sampleItemPreProcessStep")
                .tasklet(sampleItemPreProcessTasklet)
                .listener(sampleItemPreProcessTasklet)
                .build();
    }

    @Bean
    public Step sampleItemProcessStep() {
        AbstractPagingItemReader itemReader = new SamplePagingItemReader(itemService, 500);

        return stepBuilderFactory.get("sampleItemProcessStep")
                .<Item, Element>chunk(1000)
                .reader(new SamplePagingItemReader(itemService, 100))
                .writer(new SampleItemWriter())
                .processor(new SampleItemProcessor())
                .build();
    }

    @Bean
    public JobExecutionDecider samplePaginationDecider() {
        return new SamplePaginationDecider();
    }

}
