package com.dongho.batch.example.jobs.sample.step;

import com.dongho.batch.example.domain.Element;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
public class SampleItemWriter implements ItemWriter<Element> {

    @Override
    public void write(List<? extends Element> list) {
        log.info("Save chunk of element list. {}", list.size());
    }

}
