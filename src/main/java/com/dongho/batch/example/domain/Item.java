package com.dongho.batch.example.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Item {

    private int id;
    private String name;

    private int memberId;

}
