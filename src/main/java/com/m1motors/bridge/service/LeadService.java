package com.m1motors.bridge.service;

import com.m1motors.bridge.dto.ChatbotRequest;
import com.m1motors.bridge.dto.WebhookRequest;
import com.m1motors.bridge.model.Client;
import com.m1motors.bridge.model.Lead;
import com.m1motors.bridge.service.exceptions.CustomException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class LeadService {

    private final RestTemplate restTemplate;

    @Autowired
    public LeadService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void processLead(ChatbotRequest chatbotRequest) throws CustomException {
        log.info("Processando lead do chatbot");
        log.info(chatbotRequest.toString());

        Client client = createClient(chatbotRequest);
        Lead lead = createLead(chatbotRequest, client);
        WebhookRequest webhookRequest = createWebhookRequest(lead);

        sendToWebhook(webhookRequest);
    }

    private Client createClient(ChatbotRequest chatbotRequest) {
        Client client = new Client();
        client.setName(chatbotRequest.getContact().getName());
        client.setPhone(chatbotRequest.getContact().getFields().getCelular());
        client.setEmail(" ");
        return client;
    }

    private Lead createLead(ChatbotRequest chatbotRequest, Client client) {
        Lead lead = new Lead();
        lead.setClient(client);
        lead.setId((long) chatbotRequest.getId());
        lead.setTitle("LEAD do Chatbot");
        lead.setChannel("Chatbot");
        lead.setLeadType("Chatbot");
        return lead;
    }

    private WebhookRequest createWebhookRequest(Lead lead) {
        WebhookRequest webhookRequest = new WebhookRequest();
        webhookRequest.setLead(lead);
        return webhookRequest;
    }

private void sendToWebhook(WebhookRequest webhookRequest) throws CustomException {
    String webhookUrl = "https://app.revendamais.com.br/application/index.php/api/leads/help";
    try {
        restTemplate.postForObject(webhookUrl, webhookRequest, String.class);
        log.info("Lead enviado para o webhook");
        log.info(webhookRequest.toString());
    } catch (Exception e) {
        log.error("Erro ao enviar o LEAD para o Webhook", e);
        throw new CustomException("Erro ao enviar o LEAD para o Webhook: " + e.getMessage());
    }
}
}