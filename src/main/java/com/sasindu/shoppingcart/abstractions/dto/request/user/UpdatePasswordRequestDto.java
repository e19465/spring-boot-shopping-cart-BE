package com.sasindu.shoppingcart.abstractions.dto.request.user;

import lombok.Data;

@Data
public class UpdatePasswordRequestDto {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
