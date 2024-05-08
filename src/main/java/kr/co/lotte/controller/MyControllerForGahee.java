package kr.co.lotte.controller;

import jakarta.servlet.http.HttpSession;
import kr.co.lotte.dto.*;
import kr.co.lotte.entity.OrderItems;
import kr.co.lotte.entity.Orders;
import kr.co.lotte.entity.Review;
import kr.co.lotte.entity.User;
import kr.co.lotte.security.MyUserDetails;
import kr.co.lotte.service.AdminService;
import kr.co.lotte.service.MyServiceForGahee;
import kr.co.lotte.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MyControllerForGahee {
    private final AdminService adminService;
    private final ReviewService reviewService;
    private final MyServiceForGahee myServiceForGahee;

    @GetMapping("/my/coupon")
    public String myCoupon(Model model , Authentication authentication , @RequestParam(name = "state", required = false) String states) {
        if(states == null){
            states ="0";
        }
        int state = Integer.parseInt(states);

        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        String uid = userDetails.getUser().getUid();
        model.addAttribute("coupons", myServiceForGahee.findDownloadCouponByUid(state, uid));
        return "/my/coupon";
    }


    @GetMapping("/my/home")
    public String myHome(Model model , Authentication authentication , HttpSession session) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        String uid = userDetails.getUser().getUid();
        //포인트 넣어주고
        model.addAttribute("points", myServiceForGahee.forPoint(uid));


        //최근 주문 내역
        List<Orders> orders = myServiceForGahee.searchOrdersForHome(uid);
        model.addAttribute("orders", orders);
        List<List<OrderItems>> orderItems = myServiceForGahee.searchOrderItems(orders);
        //subitems (subProdNo가 나올 것이고...)
        model.addAttribute("orderItems", myServiceForGahee.searchOrderItems(orders));
        //prod image
        model.addAttribute("products",myServiceForGahee.searchProducts(orderItems));

        //상품평 출력
        List<Review> reviews= reviewService.find_five(uid);
        log.info("reviews : "+reviews);
        model.addAttribute("reviews", reviews);

        //배너 출력
        List<BannerDTO> banner5 = adminService.findMY1("MY1");
        log.info("banner5: {}", banner5);
        model.addAttribute("banner5", banner5);

        //쿠폰 유효기간 설정
        myServiceForGahee.checkCoupon(uid);

        //home네비에 있는 친구들
        myServiceForGahee.forMyHome(uid, session);
        return "/my/home";
    }

    @GetMapping("/my/order")
    public String myOrder(OrdersPageRequestDTO requestDTO, Authentication authentication, Model model) throws ParseException {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        String uid = userDetails.getUser().getUid();
        OrdersPageResponseDTO pageResponseDTO = myServiceForGahee.searchOrders(requestDTO, uid);
        List<Orders> orders = pageResponseDTO.getDtoList();
        List<List<OrderItems>> orderItems = myServiceForGahee.searchOrderItems(orders);
        //subitems (subProdNo가 나올 것이고...)
        model.addAttribute("orderItems", myServiceForGahee.searchOrderItems(orders));

        //prod image
        model.addAttribute("products",myServiceForGahee.searchProducts(orderItems));

        model.addAttribute("pageResponseDTO", pageResponseDTO);
        return "/my/order";
    }

    @GetMapping("/my/point")
    public String myPoint(PointsPageRequestDTO requestDTO, Authentication authentication , Model model) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        String uid = userDetails.getUser().getUid();
        model.addAttribute("pageResponseDTO", myServiceForGahee.searPoints(requestDTO,uid));


        return "/my/point";
    }
    @GetMapping("/my/favorite")
    public String myFavorite(Model model , Authentication authentication , ProductsPageRequestDTO requestDTO) {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        String uid = userDetails.getUser().getUid();
        ProductsPageResponseDTO pageResponseDTO = myServiceForGahee.searchLikes(requestDTO, uid);
        model.addAttribute("pageResponseDTO", pageResponseDTO);
        model.addAttribute("productList" , myServiceForGahee.searchProductsLike(pageResponseDTO.getDtoListLikes()));
        return "/my/favorite";
    }

    @GetMapping("/my/deleteLike")
    @ResponseBody
    public ResponseEntity deleteLike(@RequestParam(name = "prodNo") int prodNo , Authentication authentication ){
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        String uid = userDetails.getUser().getUid();
        return  myServiceForGahee.deleteLike(uid, prodNo);
    }

    @PostMapping("/my/deleteLike")
    @ResponseBody
    public ResponseEntity deleteLikes(@RequestBody Map<String, List<Integer> > map , Authentication authentication ){
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        String uid = userDetails.getUser().getUid();
        List<Integer> prodNos = map.get("prodNo");
        return  myServiceForGahee.deleteLikes(uid, prodNos);
    }

    @GetMapping("/my/qna")
    public String myQna() {
        return "/my/qna";
    }


    @GetMapping("/product/coupon")
    public String coupon(Model model){
        model.addAttribute("coupons", myServiceForGahee.findFutureCoupons());
        return "/product/coupon";
    }

    //쿠폰(admin)
    @GetMapping("/admin/coupon/register")
    public String couponRegister(Authentication authentication , Model model , @RequestParam(name = "code", required = false)String code ,
                                 @RequestParam(name = "state", required = false) String states){
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        if(!user.getRole().equals("ADMIN")){
            return "/";
        }
        if(code == null){
            code = "0";
        }
        //쿠폰이 보여야할텐데... 현재 가능한 것만 보여줄까?
        if(states == null){
            states = "0";
        }
        int state = Integer.parseInt(states);
        model.addAttribute("coupons", myServiceForGahee.searchCoupon(state));


        model.addAttribute("code", code);
        return "/admin/product/coupon";
    }

    @PostMapping("/admin/coupon/register")
    public String registerCoupon(CouponDTO coupon){
        log.info(coupon.toString()+"이거이거");
        myServiceForGahee.registerCoupon(coupon);
        return "redirect:/admin/coupon/register?code=100";
    }

    @PostMapping("/my/coupon/download")
    public  ResponseEntity downloadCoupon(@RequestBody DownloadCouponDTO downloadCouponDTO){
    return myServiceForGahee.donwloadCoupon(downloadCouponDTO);
    };





}