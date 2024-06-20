package com.m1motors.bridge.model;

import lombok.Data;

@Data
public class Contact {
    private String uid;
    private String type;
    private String key;
    private String name;
    private Fields fields;

}