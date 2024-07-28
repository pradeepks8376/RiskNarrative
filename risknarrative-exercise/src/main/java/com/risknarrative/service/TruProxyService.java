package com.risknarrative.service;

import com.risknarrative.constant.Constants;
import com.risknarrative.entity.Addresses;
import com.risknarrative.entity.Companies;
import com.risknarrative.entity.Officers;
import com.risknarrative.mapper.CommonMapper;
import com.risknarrative.model.*;
import com.risknarrative.repository.AddressRepository;
import com.risknarrative.repository.CompanyRepository;
import com.risknarrative.repository.OfficerRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.List;

@Service
@Slf4j
public class TruProxyService {

    @Value(Constants.COMPANY_BASE_URL)
    private String companyBaseUrl;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CommonMapper mapper;

    Logger logger = LoggerFactory.getLogger(TruProxyService.class);

    private final RestTemplate restTemplate;

    public TruProxyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CompanySearchResponse getCompanyDetails(String apiKey, String companyName, String companyNumber, boolean active) {

        String endpoint = companyBaseUrl + Constants.COMPANY_URI + companyNumber;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(Constants.API_KEY, apiKey);

        CompanySearchRequest companySearchRequest = CompanySearchRequest.builder()
                .companyName(companyName)
                .companyName(companyNumber)
                .activeOnly(active).build();

        HttpEntity<CompanySearchRequest> entity = new HttpEntity<>(companySearchRequest,headers);
        CompanySearchResponse searchResponse = restTemplate.exchange(endpoint, HttpMethod.GET, entity, CompanySearchResponse.class).getBody();
        logger.info("**** Company Search Response *****: "+searchResponse);
        OfficerResponse companyOfficers = getCompanyOfficers(apiKey, companyNumber);
        logger.info("**** Officers list *****: "+companyOfficers);

        companyService.saveCompanyOfficerDetails(searchResponse, companyOfficers);
        return mapper.buildCompanyOfficersResponse(companyService.getCompanyOfficerDetails());
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

