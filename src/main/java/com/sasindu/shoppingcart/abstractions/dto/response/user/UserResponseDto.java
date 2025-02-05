package com.sasindu.shoppingcart.abstractions.dto.response.user;


import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isEmailVerified = false;
}
