package kr.co.lotte.controller;

import groovy.util.logging.Slf4j;
import kr.co.lotte.dto.CsArticleDTO;
import kr.co.lotte.entity.CsArticle;
import kr.co.lotte.service.CsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CsController {

    private static final Logger log = LoggerFactory.getLogger(CsController.class);
    private final CsService csService;

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

    @PostMapping("/cs/qna/write")
    public String qnaWrite(CsArticleDTO csArticleDTO){


        return "redirect:/cs/qna/list";

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

    // faq.user 페이지 출력
    @GetMapping("/cs/faq/user")
    public String faqUser(Model model) {

        List<CsArticle> lotteOners = csService.getLotteonersArticles();
        model.addAttribute("lotteOners", lotteOners);

        List<CsArticle> reg = csService.getRegArticles();
        model.addAttribute("reg", reg);

        List<CsArticle> info = csService.getInfoArticles();
        model.addAttribute("info", info);

        List<CsArticle> grade = csService.getGradeArticles();
        model.addAttribute("grade", grade);

        List<CsArticle> del = csService.getDelArticles();
        model.addAttribute("del", del);

        return "/cs/faq/user";
    }

    // faq.trade 페이지 출력
    @GetMapping("/cs/faq/trade")
    public String faqTrade(Model model) {

        List<CsArticle> etcord = csService.getEtcOrdArticle();
        model.addAttribute("etcord", etcord);

        List<CsArticle> etccard = csService.getEtcCardArticle();
        model.addAttribute("etccard", etccard);

        List<CsArticle> cashreceipt = csService.getCashReceiptArticle();
        model.addAttribute("cashreceipt", cashreceipt);

        List<CsArticle> taxreceipt = csService.getTaxReceiptArticle();
        model.addAttribute("taxreceipt", taxreceipt);

        return "/cs/faq/trade";
    }

    // faq.order 페이지 출력
    @GetMapping("/cs/faq/order")
    public String faqOrder(Model model) {

        List<CsArticle> lpay = csService.getLpayArticle();
        model.addAttribute("lpay", lpay);

        List<CsArticle> etc = csService.getEtcArticle();
        model.addAttribute("etc", etc);

        List<CsArticle> mutong = csService.getMutongArticle();
        model.addAttribute("mutong", mutong);

        List<CsArticle> ord = csService.getOrdArticle();
        model.addAttribute("ord", ord);

        List<CsArticle> ordlist = csService.getOrdlistArticle();
        model.addAttribute("ordlist", ordlist);

        List<CsArticle> card = csService.getCardArticle();
        model.addAttribute("card", card);

        return "/cs/faq/order";
    }

    // faq.delivery 페이지 출력
    @GetMapping("/cs/faq/delivery")
    public String faqDelivery(Model model) {

        List<CsArticle> buy = csService.getBuyArticle();
        model.addAttribute("buy", buy);

        List<CsArticle> delp = csService.getDelpArticle();
        model.addAttribute("delp", delp);

        List<CsArticle> delm = csService.getDelmArticle();
        model.addAttribute("delm", delm);

        List<CsArticle> delinfo = csService.getDelinfoArticle();
        model.addAttribute("delinfo", delinfo);

        List<CsArticle> gift = csService.getGiftArticle();
        model.addAttribute("gift", gift);

        return "/cs/faq/delivery";
    }

    // faq.cancel 페이지 출력
    @GetMapping("/cs/faq/cancel")
    public String faqCancel(Model model) {

        List<CsArticle> ordCancel = csService.getOrdCancelArticle();
        model.addAttribute("ordCancel", ordCancel);

        List<CsArticle> refund = csService.getRefundArticle();
        model.addAttribute("refund", refund);

        List<CsArticle> as = csService.getAsArticle();
        model.addAttribute("as", as);

        List<CsArticle> asp = csService.getAspArticle();
        model.addAttribute("asp", asp);

        List<CsArticle> change = csService.getChangeArticle();
        model.addAttribute("change", change);

        List<CsArticle> returns = csService.getReturnsArticle();
        model.addAttribute("returns", returns);

        return "/cs/faq/cancel";
    }

    // faq.eventCupon 페이지 출력
    @GetMapping("/cs/faq/eventCupon")
    public String faqEventCupon(Model model) {

        List<CsArticle> lpoint = csService.getLpointArticles();
        model.addAttribute("lpoint", lpoint);

        List<CsArticle> lstamp = csService.getLstampArticles();
        model.addAttribute("lstamp", lstamp);

        List<CsArticle> review = csService.getReviewArticles();
        model.addAttribute("review", review);

        List<CsArticle> onmile = csService.getOnmileArticles();
        model.addAttribute("onmile", onmile);

        List<CsArticle> event = csService.getEventArticles();
        model.addAttribute("event", event);

        return "/cs/faq/eventCupon";
    }

    @GetMapping("/cs/faq/view")
    public String faqView(){

        return "/cs/faq/view";
    }

}
