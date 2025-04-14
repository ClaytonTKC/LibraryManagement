package org.isaacwallace.librarymanagement.DomainClient;

import org.isaacwallace.librarymanagement.Author.Presentation.Models.AuthorResponseModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthorServiceClient {

    private final RestTemplate restTemplate;

    @Value("${author.service.base-url}")
    private String SERVICE_BASE_URL;

    public AuthorServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AuthorResponseModel getAuthorByAuthorId(String authorid) {
        return restTemplate.getForObject(SERVICE_BASE_URL + "/" + authorid, AuthorResponseModel.class);
    }
}