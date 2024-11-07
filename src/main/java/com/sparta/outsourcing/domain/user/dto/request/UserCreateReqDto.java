package com.sparta.outsourcing.domain.user.dto.request;

import com.sparta.outsourcing.domain.user.entity.Address;
import com.sparta.outsourcing.domain.user.entity.User;
import com.sparta.outsourcing.domain.user.entity.UserRole;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateReqDto {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(max = 10, message = "닉네임은 10글자까지만 입력해주세요.")
    private String nickname;

    @NotBlank(message = "패스워드를 입력해주세요.")
    @Size(min = 8, max = 60, message = "패스워드는 8글자 이상 60자 이하로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "패스워드는 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함합니다. ")
    private String password;

    @NotNull(message = "권한 정보를 입력해주세요.'")
    private UserRole role;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "휴대폰 번호 양식에 맞지 않습니다.")
    private String phoneNumber;

    @NotNull(message = "주소를 입력해주세요.")
    private Address address;

    public User toEntity(){
        return User.builder()
                .email(this.email)
                .password(this.password)
                .nickname(this.nickname)
                .role(this.role)
                .phoneNumber(this.phoneNumber)
                .address(this.address)
                .build();
    }
}
