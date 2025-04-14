package org.isaacwallace.librarymanagement.DomainClient;

import org.isaacwallace.librarymanagement.Transaction.Presentation.Models.TransactionResponseModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TransactionServiceClient {

    private final RestTemplate restTemplate;

    @Value("${member.service.base-url}")
    private String SERVICE_BASE_URL;

    public TransactionServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TransactionResponseModel getTransactionByTransactionId(String transactionid) {
        return restTemplate.getForObject(SERVICE_BASE_URL + "/" + transactionid, TransactionResponseModel.class);
    }
}
