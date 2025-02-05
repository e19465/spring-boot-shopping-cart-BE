package com.sasindu.shoppingcart.models;


import com.sasindu.shoppingcart.abstractions.dto.response.user.UserResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
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
        return userResponse;
    }
}
