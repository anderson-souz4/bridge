package com.m1motors.bridge.model;

import lombok.Data;

@Data
public class Lead {
    private Client client;
    private Long id;
    private String title;
    private String channel;
    private String leadType;
    private String interation;
}