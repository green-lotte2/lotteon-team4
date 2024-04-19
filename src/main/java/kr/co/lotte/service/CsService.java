package kr.co.lotte.service;

import groovy.util.logging.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import kr.co.lotte.entity.CsFaq;
import kr.co.lotte.repository.CsRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class CsService {

    private static final Logger log = LoggerFactory.getLogger(CsService.class);
    private final CsRepository csRepository;
    private final HttpServletRequest request;

    // faq.user 출력
    public List<CsFaq> getLotteonersArticles(){
        return csRepository.findByCate("lotteOners");
    }
    public List<CsFaq> getRegArticles(){
        return csRepository.findByCate("reg");
    }
    public List<CsFaq> getInfoArticles(){
        return csRepository.findByCate("info");
    }
    public List<CsFaq> getGradeArticles(){
        return csRepository.findByCate("grade");
    }
    public List<CsFaq> getDelArticles(){
        return csRepository.findByCate("del");
    }

    // faq.eventCupon 출력
    public List<CsFaq> getLpointArticles(){
        return csRepository.findByCate("lpoint");
    }
    public List<CsFaq> getLstampArticles(){
        return csRepository.findByCate("lstamp");
    }
    public List<CsFaq> getReviewArticles(){
        return csRepository.findByCate("review");
    }
    public List<CsFaq> getOnmileArticles(){
        return csRepository.findByCate("onmile");
    }
    public List<CsFaq> getEventArticles(){
        return csRepository.findByCate("event");
    }

    // faq.ord 출력
    public List<CsFaq> getLpayArticle(){
        return csRepository.findByCate("lpay");
    }
    public List<CsFaq> getEtcArticle(){
        return csRepository.findByCate("etc");
    }
    public List<CsFaq> getMutongArticle(){
        return csRepository.findByCate("mutong");
    }
    public List<CsFaq> getOrdArticle(){
        return csRepository.findByCate("ord");
    }
    public List<CsFaq> getOrdlistArticle(){
        return csRepository.findByCate("ordlist");
    }
    public List<CsFaq> getCardArticle(){
        return csRepository.findByCate("card");
    }

    // faq.delivery 출력
    public List<CsFaq> getBuyArticle(){
        return csRepository.findByCate("buy");
    }
    public List<CsFaq> getDelpArticle(){
        return csRepository.findByCate("delp");
    }
    public List<CsFaq> getDelmArticle(){
        return csRepository.findByCate("delm");
    }
    public List<CsFaq> getDelinfoArticle(){
        return csRepository.findByCate("delinfo");
    }
    public List<CsFaq> getGiftArticle(){
        return csRepository.findByCate("gift");
    }

    // faq.cancel 출력
    public List<CsFaq> getOrdCancelArticle(){
        return csRepository.findByCate("ordcancel");
    }
    public List<CsFaq> getRefundArticle(){
        return csRepository.findByCate("refund");
    }
    public List<CsFaq> getAsArticle(){
        return csRepository.findByCate("as");
    }
    public List<CsFaq> getAspArticle(){
        return csRepository.findByCate("asp");
    }
    public List<CsFaq> getChangeArticle(){
        return csRepository.findByCate("change");
    }
    public List<CsFaq> getReturnsArticle(){
        return csRepository.findByCate("returns");
    }

    // faq.cancel 출력
    public List<CsFaq> getEtcOrdArticle(){
        return csRepository.findByCate("etcord");
    }
    public List<CsFaq> getEtcCardArticle(){
        return csRepository.findByCate("etccard");
    }
    public List<CsFaq> getCashReceiptArticle(){
        return csRepository.findByCate("cashreceipt");
    }
    public List<CsFaq> getTaxReceiptArticle(){
        return csRepository.findByCate("taxreceipt");
    }

}
