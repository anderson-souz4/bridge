package com.m1motors.bridge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.m1motors.bridge.dto.ChatbotRequest;
import com.m1motors.bridge.service.LeadService;
import com.m1motors.bridge.service.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LeadController {

    private final LeadService leadService;

    @Autowired
    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @PostMapping("/process-lead")
    public void processLead(@RequestBody ChatbotRequest chatbotRequest) throws CustomException, JsonProcessingException {
        leadService.processLead(chatbotRequest);
    }
}