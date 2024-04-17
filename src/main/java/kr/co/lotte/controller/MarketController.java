package kr.co.lotte.controller;


import kr.co.lotte.dto.*;
import kr.co.lotte.service.MarketService;
import kr.co.lotte.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MarketController {

    private final MarketService marketService;
    private final ReviewService reviewService;


    @GetMapping("/product/list")
    public String list(){

        return "/product/list";
    }

    @GetMapping("/product/view")
    public String view(Model model, int prodno, ReviewPageRequestDTO reviewPageRequestDTO){

        log.info("prodno 값 : "+prodno);

        //상품 조회
        ProductsDTO productsDTO = marketService.selectProduct(prodno);

        log.info("productsDTO : "+productsDTO);

        log.info("/product/view : 여기까지 들어오는건가?");

        //리뷰 조회
        ReviewPageResponseDTO reviewPageResponseDTO = reviewService.selectReviews(prodno,reviewPageRequestDTO);



        //리뷰 별점 - 평균, 비율 구하기
        ReviewRatioDTO reviewRatioDTO = reviewService.selectForRatio(prodno);

        model.addAttribute("product", productsDTO);//제품정보와 이미지정보도 같이 담은 productsDTO
       // model.addAttribute(marketPageRequestDTO);
        model.addAttribute("reviewPage", reviewPageResponseDTO);
        model.addAttribute(reviewRatioDTO);

        return "/product/view";
    }

    @GetMapping("/product/cart")
    public String cart(){
        return "/product/cart";

    }


    @GetMapping("/product/order")
    public String order(){

        return "/product/order";
    }


    @GetMapping("/product/search")
    public String search(){

        return "/product/search";
    }

    @GetMapping("/product/complete")
    public String complete(){

        return "/product/complete";
    }


}
