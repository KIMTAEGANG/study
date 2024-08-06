package com.study.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tokenInfo")
public class TokenInfoEntity {
    @Id
    @Column(name = "userId") private String userId;
    @Column(name = "refreshToken") private String refreshToken;
}
