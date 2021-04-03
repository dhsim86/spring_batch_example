package com.dongho.batch.example.jobs.sample;

import com.dongho.batch.example.domain.ItemService;
import com.dongho.batch.example.domain.Member;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import java.util.List;

public class SamplePaginationListener implements JobExecutionListener {

    private final ItemService itemService;

    public SamplePaginationListener(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        List<Member> memberList = itemService.getMemberList();
        jobExecution.getExecutionContext().put("memberList", memberList);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        jobExecution.getExecutionContext().remove("memberList");
    }

}
