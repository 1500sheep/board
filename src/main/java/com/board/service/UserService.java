package com.board.service;

import com.board.domain.User;
import com.board.domain.UserRepository;
import com.board.dto.LoginDTO;
import com.board.dto.SignUpDTO;
import com.board.exception.UnAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("memberService")
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User signUp(SignUpDTO signUpDTO) {
        if (userRepository.findByEmail(signUpDTO.getEmail()).isPresent()) {
            throw UnAuthenticationException.existEmail();
        }
        return userRepository.save(signUpDTO.toEntity(passwordEncoder));
    }

    public User login(LoginDTO loginDTO) {
        User maybeUser = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(UnAuthenticationException::invalidEmail);

        if (!loginDTO.matchPassword(passwordEncoder, maybeUser)) {
            throw UnAuthenticationException.invalidPassword();
        }

        return maybeUser;
    }
}
