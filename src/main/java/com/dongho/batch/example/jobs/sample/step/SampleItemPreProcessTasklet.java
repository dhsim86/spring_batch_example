package com.dongho.batch.example.jobs.sample.step;

import com.dongho.batch.example.domain.Member;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.Optional;

public class SampleItemPreProcessTasklet implements Tasklet {

    private JobExecution jobExecution;

    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution) {
        this.jobExecution = stepExecution.getJobExecution();
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
        int currentIndex = (int) Optional.ofNullable(jobExecution.getExecutionContext().get("currentIndex"))
                .orElse(-1);
        currentIndex++;
        jobExecution.getExecutionContext().put("currentIndex", currentIndex);

        return RepeatStatus.FINISHED;
    }

}
