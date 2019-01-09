package com.board.service;

import com.board.domain.FileInfo;
import com.board.domain.FileInfoRepository;
import com.board.domain.User;
import com.board.domain.UserRepository;
import com.board.dto.LoginDTO;
import com.board.dto.SignUpDTO;
import com.board.dto.UpdateDTO;
import com.board.exception.NotAllowedException;
import com.board.exception.UnAuthenticationException;
import com.board.support.domain.FileStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service("userService")
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FileStorage fileStorage;

    public User signUp(SignUpDTO signUpDTO) {

        if (userRepository.findByEmail(signUpDTO.getEmail()).isPresent()) {
            throw UnAuthenticationException.existEmail();
        }

        // 기본 유저는 별다른 권한이 없음.
//        Role role = new Role();
//        role.setRole("유저");
//        log.info("user password " + user);
//        user.setRoles(Arrays.asList(role));

        return userRepository.save(signUpDTO.toEntity(passwordEncoder));
    }

    public User login(LoginDTO loginDTO) {
        User maybeUser = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(UnAuthenticationException::invalidEmail);

        // 도대체 왜? 출력을 한번 하게 되면 그때 나온다는건가? log.info를 통해서 maybeuser를 사용해야 담겨져 온다?!
        // 질문!! 이거 없애보고 다시 에러 메세지 확인 후 질문
        log.info("loginedUser : " + maybeUser);

        if (!loginDTO.matchPassword(passwordEncoder, maybeUser)) {
            throw UnAuthenticationException.invalidPassword();
        }

        return maybeUser;
    }

    public User update(User loginedUser, UpdateDTO updateDTO, Long userId) {

        // 이렇게 sessoin에 있는걸 database에서 다시 갖고와서 확인하면 오버헤드 아닌가?
        loginedUser = userRepository.findById(loginedUser.getId()).orElseThrow(EntityNotFoundException::new);
        User findUser = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);

        if (!loginedUser.equals(findUser)) {
            throw UnAuthenticationException.invalidUser();
        }

        if (updateDTO.checkFileIsImage()) {
            FileInfo fileInfo = fileStorage.upload(updateDTO.getFile());
            fileInfoRepository.save(fileInfo);
            findUser.setProfile(fileInfo);
        }

        findUser.update(updateDTO);
        // 위와 동일한 상황
        User user = userRepository.save(findUser);
        log.info("updatedUser : " + user);

        return user;
    }

    public void delete(User user) {
        User findUser = userRepository.findById(user.getId()).orElseThrow(EntityNotFoundException::new);

        if (findUser.isDeleted()) {
            throw new NotAllowedException("이미 삭제된 회원입니다.");
        }

        findUser.changeToDeleted();
        userRepository.save(findUser);
    }

    public User getUser(User loginedUser, Long userId) {
        loginedUser = userRepository.findById(loginedUser.getId()).orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);

        if (!user.equals(loginedUser)) {
            throw UnAuthenticationException.invalidUser();
        }

        return user;
    }
}
