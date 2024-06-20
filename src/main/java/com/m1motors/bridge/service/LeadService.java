package com.m1motors.bridge.service;

import com.m1motors.bridge.dto.ChatbotRequest;
import com.m1motors.bridge.dto.WebhookRequest;
import com.m1motors.bridge.model.Client;
import com.m1motors.bridge.model.Lead;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LeadService {


    public void processLead(ChatbotRequest chatbotRequest) {
        System.out.println("Processando lead do chatbot");
        System.out.println(chatbotRequest.toString());
        Client client = new Client();
        client.setName(chatbotRequest.getContact().getName());
        client.setPhone(chatbotRequest.getContact().getFields().getCelular());
        client.setEmail(" ");

        Lead lead = new Lead();
        lead.setClient(client);
        lead.setId((long) chatbotRequest.getId());
        lead.setTitle("LEAD do Chatbot");
        lead.setChannel("Chatbot");
        lead.setLeadType("Chatbot");

        WebhookRequest webhookRequest = new WebhookRequest();
        webhookRequest.setLead(lead);
        sendToWebhook(webhookRequest);
    }

    private void sendToWebhook(WebhookRequest webhookRequest) {
//        String webhookUrl = "URL_DO_WEBHOOK";
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.postForObject(webhookUrl, webhookRequest, String.class);
        System.out.println("Lead enviado para o webhook");
        System.out.println(webhookRequest.toString());
    }

}