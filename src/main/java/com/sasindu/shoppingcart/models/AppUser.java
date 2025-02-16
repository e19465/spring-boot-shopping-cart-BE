package com.sasindu.shoppingcart.models;


import com.sasindu.shoppingcart.abstractions.dto.response.user.UserResponseDto;
import com.sasindu.shoppingcart.abstractions.enums.AppUserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @NaturalId
    @Column(length = 50, unique = true)
    private String email;

    @Column(length = 255, nullable = false)
    private String password;

    private boolean isEmailVerified = false;

    @Enumerated(EnumType.STRING)
    private AppUserRole role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;


    /**
     * Convert the user entity to a user response
     *
     * @return the user response
     */
    public UserResponseDto toUserResponse() {
        UserResponseDto userResponse = new UserResponseDto();
        userResponse.setId(this.id);
        userResponse.setFirstName(this.firstName);
        userResponse.setLastName(this.lastName);
        userResponse.setEmail(this.email);
        userResponse.setEmailVerified(this.isEmailVerified);
        userResponse.setCartId(this.cart.getId());
        return userResponse;
    }


    /**
     * Get the authorities of the user
     *
     * @return List of authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }


    /**
     * Get the email of the user
     *
     * @return the email
     */
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    /**
     * Check if the user is enabled (email verified)
     *
     * @return true if the user is enabled
     */
    @Override
    public boolean isEnabled() {
        return isEmailVerified;
    }
}
