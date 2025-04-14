package org.isaacwallace.librarymanagement.DomainClient;

import org.isaacwallace.librarymanagement.Book.Presentation.Models.BookResponseModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BookServiceClient {

    private final RestTemplate restTemplate;

    @Value("${book.service.base-url}")
    private String SERVICE_BASE_URL;

    public BookServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BookResponseModel getBookByBookId(String bookid) {
        return restTemplate.getForObject(SERVICE_BASE_URL + "/" + bookid, BookResponseModel.class);
    }
}