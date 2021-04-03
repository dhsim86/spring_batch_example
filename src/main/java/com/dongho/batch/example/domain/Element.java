package com.dongho.batch.example.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Element {

    private int id;
    private String name;

    private int memberId;

}
