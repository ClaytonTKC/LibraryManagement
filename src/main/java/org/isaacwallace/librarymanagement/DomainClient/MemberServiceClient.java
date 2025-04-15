package org.isaacwallace.librarymanagement.DomainClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.isaacwallace.librarymanagement.Employee.Presentation.Models.EmployeeResponseModel;
import org.isaacwallace.librarymanagement.Member.Presentation.Models.MemberResponseModel;
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
public class MemberServiceClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;


    private final String SERVICE_BASE_URL;

    public MemberServiceClient(RestTemplate restTemplate, ObjectMapper mapper, @Value("${member.service.base-url}") String serviceBaseUrl) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;

        this.SERVICE_BASE_URL = serviceBaseUrl;
    }

    public List<MemberResponseModel> getMembers() {
        try {
            log.debug("member-service URL is {}", SERVICE_BASE_URL);

            return this.restTemplate.getForObject(SERVICE_BASE_URL, List.class);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public MemberResponseModel getMemberByMemberId(String employeeid) {
        try {
            log.debug("member-service URL is {}", SERVICE_BASE_URL);

            return this.restTemplate.getForObject(SERVICE_BASE_URL + "/" + employeeid, MemberResponseModel.class);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public MemberResponseModel addMember(MemberResponseModel memberResponseModel) {
        try {
            log.debug("member-service URL is {}", SERVICE_BASE_URL);

            return this.restTemplate.postForObject(SERVICE_BASE_URL, memberResponseModel, MemberResponseModel.class);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public MemberResponseModel updateMember(MemberResponseModel memberResponseModel, String memberid) {
        try {
            log.debug("member-service URL is {}", SERVICE_BASE_URL + "/" + memberid);

            this.restTemplate.put(SERVICE_BASE_URL + "/" + memberid, memberResponseModel, MemberResponseModel.class);

            return this.getMemberByMemberId(memberid);
        } catch (HttpClientErrorException ex) {
            throw handleHttpClientException(ex);
        }
    }

    public void deleteMember(String memberid) {
        try {
            log.debug("member-service URL is {}", SERVICE_BASE_URL + "/" + memberid);

            this.restTemplate.delete(SERVICE_BASE_URL + "/" + memberid);
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