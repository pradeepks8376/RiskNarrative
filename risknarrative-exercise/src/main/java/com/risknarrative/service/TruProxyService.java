package com.risknarrative.service;

import com.risknarrative.constant.Constants;
import com.risknarrative.model.Company;
import com.risknarrative.model.CompanySearchRequest;
import com.risknarrative.model.CompanySearchResponse;
import com.risknarrative.model.OfficerResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.List;

@Service
@Slf4j
public class TruProxyService {

    @Value(Constants.COMPANY_BASE_URL)
    private String companyBaseUrl;

    Logger logger = LoggerFactory.getLogger(TruProxyService.class);

    private final RestTemplate restTemplate;

    public TruProxyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CompanySearchResponse getCompanyDetails(String apiKey, String companyName, String companyNumber, boolean active) {
        String endpoint = companyBaseUrl + Constants.COMPANY_URI + companyNumber;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", apiKey);
        CompanySearchRequest companySearchRequest = new CompanySearchRequest();
        companySearchRequest.setCompanyName(companyName);
        companySearchRequest.setCompanyNumber(companyNumber);
        companySearchRequest.setActiveOnly(active);

        HttpEntity<CompanySearchRequest> entity = new HttpEntity<>(companySearchRequest,headers);
        CompanySearchResponse searchResponse = restTemplate.exchange(endpoint, HttpMethod.GET, entity, CompanySearchResponse.class).getBody();
        logger.info("**** Company Search Response *****: "+searchResponse);
        OfficerResponse companyOfficers = getCompanyOfficers(apiKey, companyNumber);
        logger.info("**** Officers list *****: "+companyOfficers);

        return CompanySearchResponse.builder().items(List.of(Company.builder()
                .company_type(searchResponse.getItems().stream().findFirst().map(Company::getCompany_type).get())
                .company_number(searchResponse.getItems().stream().findFirst().map(Company::getCompany_number).get())
                .company_status(searchResponse.getItems().stream().findFirst().map(Company::getCompany_status).get())
                .date_of_creation(searchResponse.getItems().stream().findFirst().map(Company::getDate_of_creation).get())
                .title(searchResponse.getItems().stream().findFirst().map(Company::getTitle).get())
                .address(searchResponse.getItems().stream().findFirst().map(Company::getAddress).get())
                .officers(List.of(companyOfficers.getItems().stream().findFirst().get())).build()))
                .total_results(searchResponse.getItems().size()).build();
    }

    public OfficerResponse getCompanyOfficers(String apiKey, String companyNumber) {
        String endpoint = StringUtils.join(companyBaseUrl, Constants.COMPANY_OFFICER_URI, companyNumber);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(Constants.API_KEY, apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(endpoint, HttpMethod.GET, entity, OfficerResponse.class).getBody();
    }
}

