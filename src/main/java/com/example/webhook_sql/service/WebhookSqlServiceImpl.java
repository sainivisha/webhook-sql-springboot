package com.example.webhook_sql.service;

import com.example.webhook_sql.dto.QueryDto;
import com.example.webhook_sql.dto.WebHookRequest;
import com.example.webhook_sql.dto.WebHookResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * Class which is used to start the process
 *
 * @author Vishaka Saini
 */
@Slf4j
@Service
public class WebhookSqlServiceImpl implements WebhookSqlService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;
    private static final String sqlQuery = "SELECT e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME, COUNT(e2.EMP_ID) AS YOUNGER_EMPLOYEES_COUNT FROM EMPLOYEE e1 JOIN DEPARTMENT d ON e1.DEPARTMENT = d.DEPARTMENT_ID LEFT JOIN EMPLOYEE e2 ON e1.DEPARTMENT = e2.DEPARTMENT AND e1.EMP_ID != e2.EMP_ID AND e1.DOB > e2.DOB GROUP BY e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME ORDER BY e1.EMP_ID DESC";

    @Value("${generateWebhook.url}")
    private String generateWebhookUrl;

    @Value("${testWebhook.url}")
    private String testWebhookUrl;

    public WebhookSqlServiceImpl(RestTemplate restTemplate, ResourceLoader resourceLoader) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
        this.resourceLoader = resourceLoader;
    }


    @Override
    public void process() {
        log.debug("Processing Webhook Sql task");
        WebHookResponse webHookResponse = retrieveWebhookDetails();
        submitQuery(sqlQuery, webHookResponse);
    }

    private void submitQuery(String sqlQuery, WebHookResponse webHookResponse) {
        log.debug("Submitting final query based on reg no");
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.set("Authorization", webHookResponse.getAccessToken());

            QueryDto queryDto = QueryDto.builder().finalQuery(sqlQuery).build();
            HttpEntity<QueryDto> requestEntity = new HttpEntity<>(queryDto, httpHeaders);
            ResponseEntity<String> responseEntity = restTemplate.exchange(testWebhookUrl, HttpMethod.POST, requestEntity, String.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to submit query with error = " + e.getMessage(), e);
        }

        log.info("submitted final query successfully");

    }

    private WebHookResponse retrieveWebhookDetails() {
        log.debug("Retrieving web hook details");
        try {
            WebHookRequest request = WebHookRequest
                    .builder()
                    .name("Vishakha Saini")
                    .regNo("REG1292240176")
                    .email("vishaka.saini@mitwpu.edu.in")
                    .build();
            return restTemplate.postForObject(generateWebhookUrl, request, WebHookResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process with error = " + e.getMessage(), e);
        }
    }
}
