package com.ecommerce.notification_service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailContent {
    private String from;
    private String to;
    private String subject;
    private String body;
}
