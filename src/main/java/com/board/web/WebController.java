package com.board.web;

import com.board.domain.User;
import com.board.security.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class WebController {

    @GetMapping("/")
    public String home(Model model) {
        return "/home";
    }

    @GetMapping("/mypage")
    public String myPage(@LoginUser User user, Model model) {
        return "/mypage";
    }

    @GetMapping("/upload")
    public String upload(@LoginUser(required = false) User user) {
        if (user.isGuestUser()) {
            return "redirect:/login";
        }
        return "/upload";
    }

    @GetMapping("/login")
    public String login(@LoginUser(required = false) User user) {
        if (!user.isGuestUser()) {
            return "redirect:/";
        }
        return "/login";
    }

    @GetMapping("/signup")
    public String signup(@LoginUser(required = false) User user) {
        if (!user.isGuestUser()) {
            return "redirect:/";
        }
        return "/signup";
    }

    @GetMapping("/post/{id}")
    public String detail() {
        return "/detail";
    }

    @GetMapping("/admin")
    public String admin(@LoginUser User adminUser) {
        log.info("관리자 페이지 접근");
        if (!adminUser.isAdmin()) {
            return "redirect:/";
        }
        return "/admin";
    }
}
