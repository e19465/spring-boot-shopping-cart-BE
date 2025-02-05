package com.sasindu.shoppingcart.abstractions.dto.request.user;


import lombok.Data;

@Data
public class AddUserRequestDto {
    private String firstName;
    private String lastName = "";
    private String email;
    private String password;
    private String confirmPassword;
}
