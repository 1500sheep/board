package com.board.web;

import com.board.domain.User;
import com.board.dto.LoginDTO;
import com.board.dto.SignUpDTO;
import com.board.dto.UpdateDTO;
import com.board.security.LoginUser;
import com.board.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.RestResponse;
import utils.SessionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class ApiUserController {

    @Resource(name = "userService")
    private UserService userService;

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody SignUpDTO signUpDTO) {
        User user = userService.signUp(signUpDTO);
        return ResponseEntity.created(URI.create("/user/" + user.getId())).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<RestResponse> getUser(@LoginUser User loginUser, @PathVariable Long userId) {
        return ResponseEntity.ok(RestResponse.success(userService.getUser(loginUser, userId)));
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginDTO loginDTO, HttpSession session) {
        User user = userService.login(loginDTO);
        SessionUtils.setUserInSession(session, user);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> update(@LoginUser User user, @PathVariable Long userId, @Valid UpdateDTO updateDTO, HttpSession session) {
        User updateUser = userService.update(user, updateDTO, userId);
        SessionUtils.setUserInSession(session, updateUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@LoginUser User loginedUser, HttpSession session) {
        userService.delete(loginedUser);
        SessionUtils.removeUserInSession(session);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@LoginUser User loginedUser, HttpSession session) {
        SessionUtils.removeUserInSession(session);
        return ResponseEntity.ok().build();
    }
}
