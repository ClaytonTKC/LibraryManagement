package org.isaacwallace.librarymanagement.DomainClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.isaacwallace.librarymanagement.Author.Presentation.Models.AuthorRequestModel;
import org.isaacwallace.librarymanagement.Author.Presentation.Models.AuthorResponseModel;
import org.isaacwallace.librarymanagement.Utils.Exceptions.HttpErrorInfo;
import org.isaacwallace.librarymanagement.Utils.Exceptions.InvalidInputException;
import org.isaacwallace.librarymanagement.Utils.Exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class AuthorServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    private String SERVICE_BASE_URL;

    public AuthorServiceClient(RestTemplate restTemplate, ObjectMapper mapper, @Value("${author.service.base-url}") String SERVICE_BASE_URL) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;

        this.SERVICE_BASE_URL = SERVICE_BASE_URL;
    }

    public List<AuthorResponseModel> getAuthors() {
        try {
            log.debug("author-service URL is {}", SERVICE_BASE_URL);

            return this.restTemplate.getForObject(SERVICE_BASE_URL, List.class);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public AuthorResponseModel getAuthorByAuthorId(String authorid) {
        try {
            log.debug("author-service URL is {}", SERVICE_BASE_URL + "/" + authorid);

            return this.restTemplate.getForObject(SERVICE_BASE_URL + "/" + authorid, AuthorResponseModel.class);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public AuthorResponseModel addAuthor(AuthorRequestModel authorRequestModel) {
        try {
            log.debug("author-service URL is {}", SERVICE_BASE_URL);

            return this.restTemplate.postForObject(SERVICE_BASE_URL, authorRequestModel, AuthorResponseModel.class);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public AuthorResponseModel updateAuthor(AuthorResponseModel authorResponseModel, String authorid) {
        try {
            log.debug("author-service URL is {}", SERVICE_BASE_URL);

            this.restTemplate.put(SERVICE_BASE_URL, authorResponseModel);

            return this.getAuthorByAuthorId(authorid);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public void deleteAuthor(String authorid) {
        try {
            log.debug("author-service URL is {}", SERVICE_BASE_URL + "/" + authorid);

            this.restTemplate.delete(SERVICE_BASE_URL + "/" + authorid);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public String getErrorMessage(HttpClientErrorException ex) {
        try {
            return this.mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        } catch (IOException ioex) {
            return ioex.getMessage();
        }
    }

    private RuntimeException handleHttpClientException(HttpClientErrorException ex) {
        if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
            return new NotFoundException(getErrorMessage(ex));
        }

        if (ex.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) {
            return new InvalidInputException(getErrorMessage(ex));
        }

        log.warn("Got an unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
        log.warn("Error body: {}", ex.getResponseBodyAsString());

        return ex;
    }
}