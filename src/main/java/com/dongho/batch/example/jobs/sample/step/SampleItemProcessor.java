package com.dongho.batch.example.jobs.sample.step;

import com.dongho.batch.example.domain.Element;
import com.dongho.batch.example.domain.Item;
import org.springframework.batch.item.ItemProcessor;

public class SampleItemProcessor implements ItemProcessor<Item, Element> {

    @Override
    public Element process(Item item) {
        return new Element(item.getId(), item.getName(), item.getMemberId());
    }

}
