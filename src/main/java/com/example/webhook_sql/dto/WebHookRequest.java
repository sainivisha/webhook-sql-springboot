package com.example.webhook_sql.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Class which is used to keep data for WebHookRequest
 *
 * @author Vishaka Saini
 */
@Data
@Builder
public class WebHookRequest {
    private String name;
    private String regNo;
    private String email;
}
