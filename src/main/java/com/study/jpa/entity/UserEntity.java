package com.study.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "userId", length = 20) private String userId;
    @Column(name = "password", length = 100) private String password;
    @Column(name = "userName", length = 10) private String userName;
    @Column(name = "email", length = 30) private String email;
    @Column(name = "role", length = 10) private String role;
    @Column(name = "createDate") private LocalDateTime createDate;
    @Column(name = "modifyDate", nullable = false) private LocalDateTime modifyDate;

}
