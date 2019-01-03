package com.board.dto;

import com.board.domain.User;
import com.board.exception.UnAuthenticationException;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import utils.ValidateRegex;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {

    @NotEmpty
    @Pattern(regexp = ValidateRegex.EMAIL)
    private String email;

    @NotEmpty
    @Pattern(regexp = ValidateRegex.USERNAME)
    private String name;

    @NotEmpty
    @Pattern(regexp = ValidateRegex.PASSWORD)
    private String password;

    @NotEmpty
    private String confirmPassword;

    @NotEmpty
    @Pattern(regexp = ValidateRegex.PHONE)
    private String phoneNumber;

    private boolean matchPassword() {
        return password.equals(confirmPassword);
    }

    public User toEntity(PasswordEncoder passwordEncoder) throws UnAuthenticationException {
        if (!matchPassword()) {
            throw UnAuthenticationException.invalidPassword();
        }

        return User.builder()
                .name(name)
                .password(passwordEncoder.encode(password))
                .email(email)
                .phoneNumber(phoneNumber)
                .build();
    }
}
