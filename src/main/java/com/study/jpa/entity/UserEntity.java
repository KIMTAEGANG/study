package com.study.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(name = "user")
public class UserEntity {
    @Id
    @Column private String userId;
    @Column private String userName;
    @Column private String email;
}
