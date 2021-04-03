package com.dongho.batch.example.jobs.sample.step;

import com.dongho.batch.example.domain.Item;
import com.dongho.batch.example.domain.ItemService;
import com.dongho.batch.example.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.database.AbstractPagingItemReader;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SamplePagingItemReader extends AbstractPagingItemReader<Item> {

    private ItemService itemService;
    private Member targetMember;

    public SamplePagingItemReader(ItemService itemService, int pageSize) {
        this.itemService = itemService;
        setPageSize(pageSize);
    }

    @BeforeStep
    public void saveTargetMember(StepExecution stepExecution) {
        List<Member> memberList = List.class.cast(stepExecution.getJobExecution().getExecutionContext().get("memberList"));
        int currentIndex = (int) stepExecution.getJobExecution().getExecutionContext().get("currentIndex");

        this.targetMember = memberList.get(currentIndex);
    }

    @Override
    protected void doReadPage() {
        if (results == null) {
            results = new ArrayList<>();
        } else {
            results.clear();
        }

        log.info("doReadPage, memberId: {}, page: {}, pageSize: {}", targetMember.getId(), getPage(), getPageSize());
        List<Item> itemList = itemService.getItemList(targetMember.getId(), getPage(), getPageSize());
        results.addAll(itemList);
    }

    @Override
    protected void doJumpToPage(int i) {

    }

}
