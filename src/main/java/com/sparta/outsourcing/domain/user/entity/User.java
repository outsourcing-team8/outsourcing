package com.sparta.outsourcing.domain.user.entity;

import com.sparta.outsourcing.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
@SQLDelete(sql = "update users set deleted = true where user_id = ?")
@SQLRestriction("deleted = false")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String email;

    private String password;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean deleted = Boolean.FALSE;

    private String phoneNumber;

    @Embedded
    private Address address;

    @Builder
    public User(Long userId, String email, String password, String nickname, UserRole role, String phoneNumber, Address address) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
