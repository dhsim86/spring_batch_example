package com.dongho.batch.example.domain;

import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Getter
@Service
public class ItemService {

    private List<Member> memberList = new ArrayList<>();

    @PostConstruct
    public void init() {
        memberList.add(new Member(1, "member_1", 1898));
        memberList.add(new Member(2, "member_2", 2211));
        memberList.add(new Member(3, "member_3", 81));
    }

    public List<Item> getItemList(int memberId, int page, int limit) {
        Member member = memberList.get(memberId - 1);
        List<Item> itemList = new ArrayList<>();

        for (int i = 0; i < limit; i++) {
            int id = page * limit + i;

            if (id >= member.getCount()) {
                break;
            }

            itemList.add(new Item(id, String.format("item_%s_%s", memberId, id), memberId));
        }

        return itemList;
    }

}
