package com.example.webhook_sql.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Class which is used to keep data for QueryDto
 *
 * @author Vishaka Saini
 */
@Data
@Builder
public class QueryDto {
    private String finalQuery;
}
