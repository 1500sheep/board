package com.board.web;

import com.board.domain.User;
import com.board.dto.LoginDTO;
import com.board.dto.SignUpDTO;
import com.board.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.RestResponse;
import utils.SessionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/member")
public class ApiUserController {

    @Resource(name = "memberService")
    private UserService userService;

    @PostMapping
    public ResponseEntity<RestResponse> create(@Valid @RequestBody SignUpDTO signUpDTO) {
        User user = userService.signUp(signUpDTO);
        return ResponseEntity.created(URI.create("/user/" + user.getId())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse> login(@Valid @RequestBody LoginDTO loginDTO, HttpSession session) {
        User user = userService.login(loginDTO);
        SessionUtils.setUserInSession(session, user);
        return ResponseEntity.ok().build();
    }


}
