package kr.co.lotte.controller.cs;

import groovy.util.logging.Log4j2;
import kr.co.lotte.dto.CsFaqPageRequestDTO;
import kr.co.lotte.dto.CsFaqPageResponseDTO;
import kr.co.lotte.dto.CsQnaDTO;
import kr.co.lotte.service.cs.CsQnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
public class CsQnaController {

    private final CsQnaService csQnaService;

    // cs.qna reg
    @GetMapping("/cs/qna/write")
    public String qnaWrite(){
        return "/cs/qna/write";
    }

    // qna 글쓰기
    @PostMapping("/cs/qna/write")
    public String qnaWrite(CsQnaDTO csQnaDTO){
        //CsQnaDTO.setUserId("");
        csQnaService.insertQna(csQnaDTO);
        return "redirect:/cs/qna/list";
    }

    // cs.qna 전체 출력
    @GetMapping("/cs/qna/list")
    public String qnaList(Model model, CsFaqPageRequestDTO requestDTO){
        CsFaqPageResponseDTO pageResponseDTO = csQnaService.getQnaCate1andCate2(requestDTO);
        model.addAttribute("csQna", pageResponseDTO);
        return "/cs/qna/list";
    }

    // cs.qna view
    @GetMapping("/cs/qna/view")
    public String qnaView(Model model, int no){
        model.addAttribute("qnaView",csQnaService.qnaView(no));
        return "/cs/qna/view";
    }
}
