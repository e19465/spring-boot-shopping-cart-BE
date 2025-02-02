package com.sasindu.shoppingcart.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private String error;
    private String message;
    private Object data;
}
