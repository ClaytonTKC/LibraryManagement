package org.isaacwallace.librarymanagement.DomainClient;

import org.isaacwallace.librarymanagement.Employee.Presentation.Models.EmployeeResponseModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EmployeeServiceClient {

    private final RestTemplate restTemplate;

    @Value("${employee.service.base-url}")
    private String SERVICE_BASE_URL;

    public EmployeeServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EmployeeResponseModel getBookByBookId(String employeeid) {
        return restTemplate.getForObject(SERVICE_BASE_URL + "/" + employeeid, EmployeeResponseModel.class);
    }
}