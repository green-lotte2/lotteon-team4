package kr.co.lotte.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.lotte.dto.TermsDTO;
import kr.co.lotte.dto.UserDTO;
import kr.co.lotte.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/join")
    public String join() {
        return "/member/join";
    }

    @GetMapping("/member/login")
    public String login() {
        return "/member/login";
    }

    @PostMapping("/member/login")
    public String login(UserDTO userDTO, Model model) {

        memberService.login(userDTO);
        return "/index";
    }

    //회원가입 페이지 매핑
    @GetMapping("/member/register")
    public String register() {
        return "/member/register";
    }

    //타입에 따라 db에 있는지 중복확인을 시켜줌. if (type == email) {중복검사 후 메일 전송}
    @ResponseBody
    @GetMapping("/member/check/{type}/{value}")
    public ResponseEntity<?> checkUser(HttpSession session,
                                       @PathVariable("type") String type,
                                       @PathVariable("value") String value) {

        log.info("memberController.......");
        log.info("type={}", type);
        log.info("value={}", value);
        int count = memberService.selectCountMember(type, value);

        log.info("count={}", count);

        if (type.equals("email") && count <= 0) {
            log.info("email={}", value);
            memberService.sendEmailCode(session, value);
        }

        //Json 생성
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", count);

        return ResponseEntity.ok().body(resultMap);
    }

    //이메일 인증코드 검사
    @ResponseBody
    @GetMapping("/member/email/{code}")
    public ResponseEntity<?> checkEmailCode(HttpSession session, @PathVariable("code") String code) {
        String sessionCode = (String) session.getAttribute("code");

        Map<String, Object> resultMap = new HashMap<>();

        if (sessionCode.equals(code)) {
            //Json 생성
            resultMap.put("result", true);
        } else {
            //Json 생성
            resultMap.put("result", false);
        }
        return ResponseEntity.ok().body(resultMap);
    }


    //회원가입 진행
    @PostMapping("/member/register")
    public String register(HttpSession session, HttpServletRequest req, UserDTO userDTO, Model model) {
        userDTO.setRegip(req.getRemoteAddr());
        userDTO.setSms((String) session.getAttribute("sms"));


        //session 선택 동의여부, 이메일 인증 코드 session 삭제
        session.removeAttribute("sms");
        session.removeAttribute("code");
        memberService.insert(userDTO);
        model.addAttribute("message", "회원가입이 완료 되었습니다.");
        model.addAttribute("searchUrl", "/lotteshop/member/login");
        return "/member/message";
    }

    @GetMapping("/member/registerseller")
    public String registerSeller() {
        return "/member/registerSeller";
    }

    //약관 동의 페이지 매핑
    @GetMapping("/member/signup")
    public String signUp(TermsDTO termsDTO, Model model) {
        model.addAttribute(termsDTO);
        return "/member/signup";
    }

    //약관 동의 선택동의 사항 체크 여부 session에 저장
    @PostMapping("/member/signup")
    public String signUp(HttpSession session, String agree4) {

        //session 값 설정
        session.setAttribute("sms", agree4);
        return "redirect:/member/register";
    }

}
