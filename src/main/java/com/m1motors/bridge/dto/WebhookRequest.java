package com.m1motors.bridge.dto;


import com.m1motors.bridge.model.Lead;
import lombok.Data;

@Data
public class WebhookRequest {
    private Lead lead;
}
