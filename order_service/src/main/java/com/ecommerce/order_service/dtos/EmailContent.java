package com.ecommerce.order_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmailContent {
    private String from;
    private String to;
    private String subject;
    private String body;
}
