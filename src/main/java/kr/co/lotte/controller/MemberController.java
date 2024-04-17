package kr.co.lotte.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.lotte.dto.TermsDTO;
import kr.co.lotte.dto.UserDTO;
import kr.co.lotte.entity.User;
import kr.co.lotte.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final UserService service;
    ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/join")
    public String join() {
        return "/member/join";
    }

    @GetMapping("/login")
    public String login() {
        return "/member/login";
    }

    @PostMapping("/login")
    public String login(UserDTO userDTO, Model model) {

        service.login(userDTO);
        model.addAttribute("message", "회원가입이 완료 되었습니다.");
        model.addAttribute("searchUrl", "/member/login");
        return "/index";
    }

    @GetMapping("/register")
    public String register() {
        return "/member/register";
    }

    @PostMapping("/register")
    public String register(HttpSession session, HttpServletRequest req, UserDTO userDTO, Model model) {
        userDTO.setRegip(req.getRemoteAddr());
        userDTO.setSms((String) session.getAttribute("sms"));
        User user = modelMapper.map(userDTO, User.class);
        service.insert(user);
        model.addAttribute("message", "회원가입이 완료 되었습니다.");
        model.addAttribute("searchUrl", "/member/login");
        return "/message";
    }

    @GetMapping("/registerseller")
    public String registerSeller() {
        return "/member/registerSeller";
    }

    @GetMapping("/signup")
    public String signUp(TermsDTO termsDTO, Model model) {
        model.addAttribute(termsDTO);
        return "/member/signup";
    }

    @PostMapping("/signup")
    public String signUp(HttpSession session, String agree4) {
        session.setAttribute("sms", agree4);
        return "redirect:/member/register";
    }

}
