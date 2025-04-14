package org.isaacwallace.librarymanagement.DomainClient;

import org.isaacwallace.librarymanagement.Member.Presentation.Models.MemberResponseModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MemberServiceClient {

    private final RestTemplate restTemplate;

    @Value("${member.service.base-url}")
    private String SERVICE_BASE_URL;

    public MemberServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public MemberResponseModel getMemberByMemberId(String memberid) {
        return restTemplate.getForObject(SERVICE_BASE_URL + "/" + memberid, MemberResponseModel.class);
    }
}