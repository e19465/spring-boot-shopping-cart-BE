package com.sasindu.shoppingcart.repository;

import com.sasindu.shoppingcart.abstractions.enums.AppUserRole;
import com.sasindu.shoppingcart.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Check if a role exists by name
     *
     * @param role the role to check
     * @return true if the role exists, false otherwise
     */
    boolean existsByName(AppUserRole role);
}
