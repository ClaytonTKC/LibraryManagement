package org.isaacwallace.librarymanagement.DomainClient;

import org.isaacwallace.librarymanagement.Inventory.Presentation.Models.InventoryResponseModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class InventoryServiceClient {

    private final RestTemplate restTemplate;

    @Value("${inventory.service.base-url}")
    private String SERVICE_BASE_URL;

    public InventoryServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public InventoryResponseModel getBookByBookId(String bookid) {
        return restTemplate.getForObject(SERVICE_BASE_URL + "/" + bookid, InventoryResponseModel.class);
    }
}