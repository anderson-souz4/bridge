package com.m1motors.bridge.controller;

import com.m1motors.bridge.dto.ChatbotRequest;
import com.m1motors.bridge.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LeadController {

    @Autowired
    private LeadService leadService;

    @PostMapping("/process-lead")
    public void processLead(@RequestBody ChatbotRequest chatbotRequest) {
        leadService.processLead(chatbotRequest);
    }
}