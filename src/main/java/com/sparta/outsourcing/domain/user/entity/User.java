package com.sparta.outsourcing.domain.user.entity;

import com.sparta.outsourcing.common.entity.BaseEntity;
import com.sparta.outsourcing.domain.user.dto.request.UserCreateReqDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = false)
    private String phoneNumber;

    @Embedded
    private Address address;

    @Builder
    public User(String email, String password, String nickname, UserRole role, String phoneNumber, Address address) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
