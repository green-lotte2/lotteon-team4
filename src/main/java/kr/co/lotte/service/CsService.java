package kr.co.lotte.service;

import groovy.util.logging.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import kr.co.lotte.entity.CsArticle;
import kr.co.lotte.repository.CsRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.ls.LSException;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class CsService {

    private static final Logger log = LoggerFactory.getLogger(CsService.class);
    private final CsRepository csRepository;
    private final HttpServletRequest request;

    // faq.user 출력
    public List<CsArticle> getLotteonersArticles(){
        return csRepository.findByCate("lotteOners");
    }
    public List<CsArticle> getRegArticles(){
        return csRepository.findByCate("reg");
    }
    public List<CsArticle> getInfoArticles(){
        return csRepository.findByCate("info");
    }
    public List<CsArticle> getGradeArticles(){
        return csRepository.findByCate("grade");
    }
    public List<CsArticle> getDelArticles(){
        return csRepository.findByCate("del");
    }

    // faq.eventCupon 출력
    public List<CsArticle> getLpointArticles(){
        return csRepository.findByCate("lpoint");
    }
    public List<CsArticle> getLstampArticles(){
        return csRepository.findByCate("lstamp");
    }
    public List<CsArticle> getReviewArticles(){
        return csRepository.findByCate("review");
    }
    public List<CsArticle> getOnmileArticles(){
        return csRepository.findByCate("onmile");
    }
    public List<CsArticle> getEventArticles(){
        return csRepository.findByCate("event");
    }

    // faq.ord 출력
    public List<CsArticle> getLpayArticle(){
        return csRepository.findByCate("lpay");
    }
    public List<CsArticle> getEtcArticle(){
        return csRepository.findByCate("etc");
    }
    public List<CsArticle> getMutongArticle(){
        return csRepository.findByCate("mutong");
    }
    public List<CsArticle> getOrdArticle(){
        return csRepository.findByCate("ord");
    }
    public List<CsArticle> getOrdlistArticle(){
        return csRepository.findByCate("ordlist");
    }
    public List<CsArticle> getCardArticle(){
        return csRepository.findByCate("card");
    }

    // faq.delivery 출력
    public List<CsArticle> getBuyArticle(){
        return csRepository.findByCate("buy");
    }
    public List<CsArticle> getDelpArticle(){
        return csRepository.findByCate("delp");
    }
    public List<CsArticle> getDelmArticle(){
        return csRepository.findByCate("delm");
    }
    public List<CsArticle> getDelinfoArticle(){
        return csRepository.findByCate("delinfo");
    }
    public List<CsArticle> getGiftArticle(){
        return csRepository.findByCate("gift");
    }

    // faq.cancel 출력
    public List<CsArticle> getOrdCancelArticle(){
        return csRepository.findByCate("ordcancel");
    }
    public List<CsArticle> getRefundArticle(){
        return csRepository.findByCate("refund");
    }
    public List<CsArticle> getAsArticle(){
        return csRepository.findByCate("as");
    }
    public List<CsArticle> getAspArticle(){
        return csRepository.findByCate("asp");
    }
    public List<CsArticle> getChangeArticle(){
        return csRepository.findByCate("change");
    }
    public List<CsArticle> getReturnsArticle(){
        return csRepository.findByCate("returns");
    }

    // faq.cancel 출력
    public List<CsArticle> getEtcOrdArticle(){
        return csRepository.findByCate("etcord");
    }
    public List<CsArticle> getEtcCardArticle(){
        return csRepository.findByCate("etccard");
    }
    public List<CsArticle> getCashReceiptArticle(){
        return csRepository.findByCate("cashreceipt");
    }
    public List<CsArticle> getTaxReceiptArticle(){
        return csRepository.findByCate("taxreceipt");
    }

}
