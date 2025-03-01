package com.sasindu.shoppingcart.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sasindu.shoppingcart.helpers.HelperUtilStaticMethods;
import com.sasindu.shoppingcart.security.services.AppUserDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Component

public class JWTAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtils _jwtUtils;

    @Autowired
    private AppUserDetailsService _appUserDetailsService;

    /**
     * Do filter internal
     *
     * @param request     request
     * @param response    response
     * @param filterChain filter chain
     * @throws ServletException if there is an error
     * @throws IOException      if there is an error
     */
    @Override
    protected void doFilterInternal(
            @Nullable HttpServletRequest request,
            @Nullable HttpServletResponse response,
            @Nullable FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            if (request == null || response == null || filterChain == null) {
                throw new RuntimeException("Request, response or filter chain is null");
            }

            // Get the requested URL
//            String requestURI = request.getRequestURI();

            // Check if the URL is public and bypass token validation
//            if (Arrays.stream(ApplicationConstants.PUBLIC_URLS).anyMatch(requestURI::startsWith)) {
//                filterChain.doFilter(request, response);
//                return;
//            }

            // Get the access token from the request, if it is not valid, then continue
            String accessToken = HelperUtilStaticMethods.getCookieFromRequest(request, "access");
            if (!StringUtils.hasText(accessToken) || !_jwtUtils.isAccessTokenValid(accessToken)) {
                filterChain.doFilter(request, response);
                return;
            }

            String email = _jwtUtils.getEmailFromAccessToken(accessToken);
            UserDetails userDetails = _appUserDetailsService.loadUserByUsername(email);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException | UsernameNotFoundException e) {
            assert response != null;

            // Construct JSON response
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", null);
            errorResponse.put("data", null);

            if (e instanceof JwtException) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                errorResponse.put("error", "Unauthorized access");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                errorResponse.put("error", "User not found");
            }
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            // Convert to JSON and write response
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            return;
        } catch (Exception e) {
            if (response != null) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("message", null);
                errorResponse.put("data", null);
                errorResponse.put("error", "Internal server error");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                ObjectMapper objectMapper = new ObjectMapper();
                response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                return;
            } else {
                throw new RuntimeException(e);
            }
        }
        filterChain.doFilter(request, response);
    }
}
