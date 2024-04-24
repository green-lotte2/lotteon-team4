package kr.co.lotte.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CompanyController {

    @GetMapping("/company/culture")
    public String culture(){

        return "/company/culture";
    }
}
