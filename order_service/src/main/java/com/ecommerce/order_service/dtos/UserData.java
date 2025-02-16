package com.ecommerce.order_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserData {
    private Long id;
    private String name;
    private String email;
    private String password;
}
