package kr.co.lotte.controller;


import com.nimbusds.jose.shaded.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.lotte.dto.*;
import kr.co.lotte.entity.Products;
import kr.co.lotte.entity.SubProducts;
import kr.co.lotte.service.MarketService;
import kr.co.lotte.service.MemberService;
import kr.co.lotte.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MarketController {

    private final MarketService marketService;
    private final MemberService memberService;
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

        //subProducts에서 prodno로 조회, color과 size의 리스트를 들고 온다

        List<SubProducts> Options = marketService.findAllByProdNo(prodno);

        log.info("Options : "+Options.size());

        log.info("options : "+Options);

        log.info("view - getMapping - productsDTO : "+productsDTO);

        log.info("/product/view : 여기까지 들어오는건가?");

        model.addAttribute("options", Options);

        //리뷰 조회
        ReviewPageResponseDTO reviewPageResponseDTO = reviewService.selectReviews(prodno,reviewPageRequestDTO);



        //리뷰 별점 - 평균, 비율 구하기
        ReviewRatioDTO reviewRatioDTO = reviewService.selectForRatio(prodno);

        model.addAttribute("product", productsDTO);//제품정보와 이미지정보도 같이 담은 productsDTO
        model.addAttribute("reviewPage", reviewPageResponseDTO);
        model.addAttribute(reviewRatioDTO);

        return "/product/view";
    }



    //주문한 목록과 사용자 아이디,상품번호를 받고 세션에 저장함
    @PostMapping("/product/view")
    public ResponseEntity<Map<String, String>> view(@RequestBody Map<String, Object> result,  HttpServletRequest request){


        String uid = result.get("uid").toString();
        int total = Integer.parseInt(result.get("total").toString());//안쓸 듯... 다시 계산
        int prodId = Integer.parseInt(result.get("prodId").toString());

        //orders 배열을 받음
        List<Map<String, Object>> orders = (List<Map<String, Object>>) result.get("orderList");

        HttpSession session = request.getSession();

        session.setAttribute("orderList", orders);//주문한 목록 세션에 저장

        //상품 번호를 이용해서 상품에 대한 정보를 불러오기
        ProductsDTO productsDTO = marketService.selectProduct(prodId);

        log.info("productDTO : "+productsDTO);

        session.setAttribute("productsDTO", productsDTO);//상품번호 저장

        log.info("uid : "+uid);

        UserDTO userDTO = memberService.findUser(uid);//사용자 아이디를 통해 사용자에 대한 정보를 불러옴

        log.info("userDTO : "+userDTO);

        session.setAttribute("userDTO", userDTO);//사용자의 정보를 세션에 저장하기

        log.info("여기까지 오는거니? marketController-view");

        Map<String, String> response = new HashMap<>();
        response.put("result","1");
        return ResponseEntity.ok().body(response);

    }

    @GetMapping("/product/order")//여기는 바로 구매 했을 때 넘겨주는 페이지(세션으로 넘김)
    public String order(HttpServletRequest request, Model model){

        //상품에 대한 이미지가 있어야하네...

        HttpSession session = request.getSession();

        List<Map<String, Object>> ordersDTOS = (List<Map<String, Object>>) session.getAttribute("orderList");

        ProductsDTO products= (ProductsDTO) session.getAttribute("productsDTO");

        log.info("ordersDTOS : "+ordersDTOS);


        UserDTO userDTO = (UserDTO) session.getAttribute("userDTO");

        model.addAttribute("product", products);
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("ordersDTOS", ordersDTOS);

        return "/product/order";
    }

    @GetMapping("/product/cart")
    public String cart(){
        return "/product/cart";

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
