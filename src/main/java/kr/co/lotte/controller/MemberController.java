package kr.co.lotte.controller;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.lotte.dto.UserDTO;
import kr.co.lotte.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final UserService service;

    @GetMapping("/join")
    public String join() {
        return "/member/join";
    }

    @GetMapping("/login")
    public String login() {
        return "/member/login";
    }

    @GetMapping("/register")
    public String register() {
        return "/member/register";
    }

    @PostMapping("/register")
    public String register(HttpServletRequest req, UserDTO userDTO) {
        String regip = req.getRemoteUser();
        userDTO.setRegip(regip);

        service.register(userDTO.toEntity());
        return "/message";
    }

    @GetMapping("/registerseller")
    public String registerSeller() {

        return "/member/registerSeller";
    }

    @GetMapping("/signup")
    public String signUp() {
        return "/member/signup";
    }

}
