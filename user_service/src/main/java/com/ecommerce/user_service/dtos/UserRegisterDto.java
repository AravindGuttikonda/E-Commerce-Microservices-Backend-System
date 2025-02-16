package com.ecommerce.user_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRegisterDto {
    private String name;
    private String email;
    private String mobile;
}
