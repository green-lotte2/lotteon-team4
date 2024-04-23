package kr.co.lotte.controller;


import kr.co.lotte.dto.*;
import kr.co.lotte.entity.Banner;
import kr.co.lotte.entity.Categories;
import kr.co.lotte.repository.BannerRepository;
import kr.co.lotte.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.AlgorithmConstraints;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/admin/index")
    public String adminIndex(){
        return "/admin/index";
    }

    //config
    @GetMapping("/admin/config/banner")
    public String banner(Model model){

        List<BannerDTO> banner = adminService.findMAIN1("MAIN1");

        log.info("AdminController - banner : "+banner.toString());

        model.addAttribute("banner", banner);

        /*
        adminService.findMAIN2();
        adminService.findPRODUCT1();
        adminService.findMEMBER1();
        adminService.findMY1();

         */

        return "/admin/config/banner";
    }

    //info
    @GetMapping("/admin/config/info")
    public String info(Model model, @RequestParam(name = "code", required = false)String code){
        if(code == null){
            code ="0";
        }
        model.addAttribute("code",code);
        log.info("adnin!!"+adminService.findTerms(1).getSms());
        model.addAttribute("terms", adminService.findTerms(1));
        return "/admin/config/info";
    }

    //info modify
    @PostMapping("/admin/info/modify")
    public String modifyInfo( TermsDTO termsDTO){
        adminService.modifyTerms(termsDTO);
        return "redirect:/admin/config/info?code=100";
    }



    //product
    @GetMapping("/admin/product/list")
    public String list(Model model, ProductsPageRequestDTO pageRequestDTO){
        ProductsPageResponseDTO pageResponseDTO = adminService.searchProducts(pageRequestDTO);
        model.addAttribute("page", pageResponseDTO);
        return "/admin/product/list";
    }

    @GetMapping("/admin/product/register")
    public String register(Model model, @RequestParam(name="code" , required = false) String code){
        List<Categories> list = adminService.searchCategories();
        List<String> cateNameLists = list.stream().map(categories -> categories.getCateName()).toList();
        Set<String> cateNames = cateNameLists.stream().collect(Collectors.toSet());
        log.info(list.toString());
        model.addAttribute("cates", list);
        model.addAttribute("cateNames",cateNames);
        if(code == null){
            model.addAttribute("code", 0);
        }else{
            model.addAttribute("code", code);
        }
        return "/admin/product/register";
    }


    @ResponseBody
    @PostMapping("/admin/product/selectSecondCate")
    public ResponseEntity selectSecondCate(@RequestBody Map<String, String> map){
        String name = map.get("cate");
        return adminService.searchCategoriesSecondNames(name);
    }

    @ResponseBody
    @PostMapping("/admin/product/selectThridCate")
    public ResponseEntity selectThridCate(@RequestBody Map<String, String> map){
        String name = map.get("cate");
        return adminService.searchCategoriesThridNames(name);
    }

    @PostMapping("/admin/product/register")
    public String register( ProductsDTO productsDTO){
        log.info(productsDTO.toString());
        adminService.productRegister(productsDTO);
      return  "redirect:/admin/product/register?code=100";
    }


    @ResponseBody
    @PostMapping("/admin/product/subOption")
    public ResponseEntity registersubOption(@RequestBody List<SubProductsDTO> subProductsDTOS){
        log.info(subProductsDTOS.toString());
        return adminService.insertSubOptions(subProductsDTOS);
    };

    //상품수정
    @GetMapping("/admin/product/modify")
    public String modify(Model model, @RequestParam int prodNo){
        log.info(prodNo+"prodNo");
        model.addAttribute("products", adminService.findOnlyOneProduct(prodNo));
        model.addAttribute("subProducts", adminService.subProductsFind(prodNo));
        return "/admin/product/modify";
    }

    //아 상품수정 나중에 할래 귀찮다


    //상품 삭제
    @ResponseBody
    @PutMapping("/admin/product/delete")
    public ResponseEntity delete(@RequestBody Map<String, List<Integer>> map){
        List<Integer> lists = map.get("list");
        log.info(lists.toString());
        adminService.deleteProducts(lists);
        Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        return ResponseEntity.ok().body(map);
    }
    //하나만 삭제
    @PostMapping("/admin/product/deleteOne")
    public void deleteOne(@RequestBody Map<String, Integer> map){
        int subNo = map.get("number");
        log.info(subNo+"subNo");
        adminService.deleteProduct(subNo);
    }


    //배너에 등록에 대한 컨트롤러
    @ResponseBody
    @PostMapping("/banner/register")
    public ResponseEntity<String> register(BannerDTO bannerDTO){

        log.info("정상적으로 여기에 들어와지니?");

        adminService.bannerReigser(bannerDTO);//이미지 등록완료!

        log.info(bannerDTO.toString());//제대로 들어와짐

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("/lotteshop/admin/config/banner")).build();

    }

    //선택삭제를 위한 컨트롤러
    @PostMapping("/banner/delete")
    public ResponseEntity<?> SelectDelete(@RequestBody Map<String, String> requestData){

        log.info("삭제하려고 controller로 왔습니다.");

        String bannerNo = requestData.get("bannerNo");

        log.info("bannerNo:"+bannerNo);

        adminService.bannerDelete(bannerNo);

        log.info("BannerNo로 삭제를 성공했느냐? ");

        Map<String, String> result = new HashMap<>();
        result.put("data","1");
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/banner/active")
    public String bannerActive(@RequestParam("bannerNo") String bannerNo, Model model){

        log.info("bannerNo : "+bannerNo);//배너번호 잘 넘어옴

        BannerDTO bannerDTO = adminService.findById(bannerNo);//배너번호를 이용해서 설정하기 내용은 읽어오기!

        model.addAttribute("bannerDTO", bannerDTO);//활성화한 배너에 대한 정보가 들어있음

        return "redirect:/admin/config/banner";

    }
}
