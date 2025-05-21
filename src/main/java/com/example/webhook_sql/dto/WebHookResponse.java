package com.example.webhook_sql.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Class which is used to keep data for webhook dto
 *
 * @author Vishaka Saini
 */
@Data
@Builder
public class WebHookResponse {
    private String webhook;
    private String accessToken;
}
