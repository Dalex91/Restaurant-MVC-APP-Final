package com.restaurant_mvc.app.domain;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Data
public class User {
    @Id
    @Column(length = 55)
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserType userType;
}

