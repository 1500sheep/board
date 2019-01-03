package com.board.dto;

import com.board.domain.User;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import utils.ValidateRegex;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @NotEmpty
    @Pattern(regexp = ValidateRegex.EMAIL)
    private String email;

    @NotEmpty
    @Pattern(regexp = ValidateRegex.PASSWORD)
    private String password;

    public boolean matchPassword(PasswordEncoder passwordEncoder, User user) {
        return passwordEncoder.matches(password, user.getPassword());
    }
}
