package org.isaacwallace.librarymanagement.DomainClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.isaacwallace.librarymanagement.Transaction.Presentation.Models.TransactionResponseModel;
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
public class TransactionServiceClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    private final String SERVICE_BASE_URL;

    public TransactionServiceClient(RestTemplate restTemplate, ObjectMapper mapper, @Value("${transaction.service.base-url}") String SERVICE_BASE_URL) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;

        this.SERVICE_BASE_URL = SERVICE_BASE_URL;
    }

    public List<TransactionResponseModel> getTransactions() {
        try {
            log.debug("transaction-service URL is {}", SERVICE_BASE_URL);

            return this.restTemplate.getForObject(SERVICE_BASE_URL, List.class);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public TransactionResponseModel getTransactionByTransactionId(String transactionid) {
        try {
            log.debug("transaction-service URL is {}", SERVICE_BASE_URL + "/" + transactionid);

            return this.restTemplate.getForObject(SERVICE_BASE_URL + "/" + transactionid, TransactionResponseModel.class);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public TransactionResponseModel addTransaction(TransactionResponseModel transactionResponseModel) {
        try {
            log.debug("transaction-service URL is {}", SERVICE_BASE_URL);

            return this.restTemplate.postForObject(SERVICE_BASE_URL, transactionResponseModel, TransactionResponseModel.class);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public TransactionResponseModel updateTransaction(TransactionResponseModel transactionResponseModel, String transactionid) {
        try {
            log.debug("transaction-service URL is {}", SERVICE_BASE_URL + "/" + transactionid);

            this.restTemplate.put(SERVICE_BASE_URL + "/" + transactionid, transactionResponseModel);

            return this.getTransactionByTransactionId(transactionid);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public void deleteTransaction(String transactionid) {
        try {
            log.debug("transaction-service URL is {}", SERVICE_BASE_URL + "/" + transactionid);

            this.restTemplate.delete(SERVICE_BASE_URL + "/" + transactionid);
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
