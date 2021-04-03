package com.dongho.batch.example.jobs.sample;

import com.dongho.batch.example.domain.Member;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.List;

public class SamplePaginationDecider implements JobExecutionDecider {

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        List<Member> memberList = List.class.cast(jobExecution.getExecutionContext().get("memberList"));
        int currentIndex = (int) jobExecution.getExecutionContext().get("currentIndex");

        if (memberList.size() > currentIndex) {
            return new FlowExecutionStatus(RepeatStatus.CONTINUABLE.name());
        }

        return new FlowExecutionStatus(RepeatStatus.FINISHED.name());
    }

}
