package com.sasindu.shoppingcart.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT Authentication Entry Point
 */
@Component
public class JWTAuthEntryPoint implements AuthenticationEntryPoint {

    /**
     * Commence the request
     *
     * @param request       request
     * @param response      response
     * @param authException authentication exception
     * @throws IOException      if there is an error
     * @throws ServletException if there is an error
     */
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        try {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            final Map<String, Object> body = new HashMap<>();
            body.put("error", "Unauthorized");
            body.put("message", null);
            body.put("data", null);

            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), body);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
