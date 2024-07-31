package com.risknarrative.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.risknarrative.constant.Constants;
import com.risknarrative.model.CompanySearchRequest;
import com.risknarrative.service.TruProxyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

public class TruProxyServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TruProxyService truProxyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCompanyDetails() throws URISyntaxException, IOException {
        String targetUrl = "https://exercise.trunarrative.cloud/TruProxyAPI/rest/Companies/v1/Search?Query=06500244";
        HttpMethod method = HttpMethod.GET;
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("x-api-key", "PwewCEztSW7XlaAKqkg4IaOsPelGynw6SN9WsbNf");

        CompanySearchRequest companySearchRequest = CompanySearchRequest.builder()
                .companyName("BBC")
                .companyName("06500244")
                .activeOnly(true).build();

        HttpEntity<CompanySearchRequest> entity = new HttpEntity<>(companySearchRequest,headers);

        URI uri = new URI(targetUrl);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addAll(headers);
        httpHeaders.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

        String expectedResponseBody = readJsonFromFile("src/test/resources/companyresponse.json");

        ResponseEntity<String> responseEntity = new ResponseEntity<>(expectedResponseBody, HttpStatus.OK);

        when(restTemplate.exchange(uri, method, entity, String.class)).thenReturn(responseEntity);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponseBody, responseEntity.getBody());
    }

    @Test
    public void testCompanyOfficers() throws URISyntaxException, IOException {
        String url = "https://exercise.trunarrative.cloud/TruProxyAPI/rest/Companies/v1/Officers?CompanyNumber=06500244";
        HttpMethod method = HttpMethod.GET;
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("x-api-key", "PwewCEztSW7XlaAKqkg4IaOsPelGynw6SN9WsbNf");

        CompanySearchRequest companySearchRequest = CompanySearchRequest.builder()
                .companyName("BBC")
                .companyName("06500244")
                .activeOnly(true).build();

        HttpEntity<CompanySearchRequest> entity = new HttpEntity<>(companySearchRequest,headers);

        URI uri = new URI(url);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addAll(headers);
        httpHeaders.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

        String expectedResponseBody = readJsonFromFile("src/test/resources/officerresponse.json");

        ResponseEntity<String> responseEntity = new ResponseEntity<>(expectedResponseBody, HttpStatus.OK);

        when(restTemplate.exchange(uri, method, entity, String.class)).thenReturn(responseEntity);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponseBody, responseEntity.getBody());
    }

    public static String readJsonFromFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
