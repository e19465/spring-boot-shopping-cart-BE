package com.sasindu.shoppingcart.data;

import com.sasindu.shoppingcart.abstractions.enums.AppUserRole;
import com.sasindu.shoppingcart.models.Role;
import com.sasindu.shoppingcart.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final RoleRepository _roleRepository;

    /**
     * Initialize some seed data on application startup
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        initializeRoles();
    }


    /**
     * Initialize roles in application startup
     */
    private void initializeRoles() {
        List<AppUserRole> roles = Arrays.asList(AppUserRole.ROLE_ADMIN, AppUserRole.ROLE_USER);
        for (AppUserRole role : roles) {
            if (!_roleRepository.existsByName(role)) {
                _roleRepository.save(new Role(null, role));
            }
        }
    }
}
