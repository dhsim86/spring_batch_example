package com.dongho.batch.example.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public class Member implements Serializable {

    private static final long serialVersionUID = -399810264982202045L;

    private int id;
    private String name;

    private int count;

}
