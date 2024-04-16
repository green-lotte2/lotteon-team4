package kr.co.lotte.controller;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CsController {

    @GetMapping(value = {"/cs","/cs/index"} )
    public String index(){

        return "/cs/index";
    }

    @GetMapping("/cs/qna/list")
    public String qnaList(Model model, Integer pageNum, Integer pageSize){

        return "/cs/qna/list";
    }

    @GetMapping("/cs/qna/write" )
    public String qnaWrite(){

        return "/cs/qna/write";
    }

    @GetMapping("/cs/notice/list" )
    public String noticeList(){

        return "/cs/notice/list";
    }

    @GetMapping("/cs/notice/write" )
    public String noticeWrite(){

        return "/cs/notice/write";
    }

    @GetMapping("/cs/notice/view" )
    public String noticeView(){

        return "/cs/notice/view";
    }

    @GetMapping("/cs/faq/list")
    public String faqList(){

        return "/cs/faq/list";
    }

}
