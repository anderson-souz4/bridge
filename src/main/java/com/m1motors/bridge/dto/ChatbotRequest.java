package com.m1motors.bridge.dto;

import com.m1motors.bridge.model.Contact;
import lombok.Data;

import java.util.Map;

@Data
public class ChatbotRequest {
    private int id;
    private String text;
    private Contact contact;
    private Map<String, Object> data;
}