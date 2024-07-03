package com.m1motors.bridge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.m1motors.bridge.dto.ChatbotRequest;
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

    public void processLead(ChatbotRequest chatbotRequest) throws CustomException, JsonProcessingException {
        log.info("Processando lead do chatbot");
        log.info(chatbotRequest.toString());

        Client client = createClient(chatbotRequest);
        Lead lead = createLead(chatbotRequest, client);

        sendToWebhook(lead);
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
        lead.setInteration(chatbotRequest.getText());
        return lead;
    }

    private void sendToWebhook(Lead lead) throws CustomException {
        String webhookUrl = "https://app.revendamais.com.br/application/index.php/api/leads/help";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(lead);
            // Imprimir JSON
            System.out.println(json);
            restTemplate.postForObject(webhookUrl, json, String.class);
            log.info("Lead enviado para o webhook");
        } catch (Exception e) {
            log.error("Erro ao enviar o LEAD para o Webhook", e);
            throw new CustomException("Erro ao enviar o LEAD para o Webhook: " + e.getMessage());
        }
    }
}
