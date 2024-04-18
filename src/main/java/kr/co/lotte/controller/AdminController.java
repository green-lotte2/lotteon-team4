package kr.co.lotte.controller;


import kr.co.lotte.dto.ProductsDTO;
import kr.co.lotte.dto.ProductsPageRequestDTO;
import kr.co.lotte.dto.ProductsPageResponseDTO;
import kr.co.lotte.dto.SubProductsDTO;
import kr.co.lotte.entity.Categories;
import kr.co.lotte.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String banner(){
        return "/admin/config/banner";
    }

    @GetMapping("/admin/config/info")
    public String info(Model model){
        model.addAttribute("terms", adminService.findTerms());
        return "/admin/config/info";
    }

    //product
    @GetMapping("/admin/product/list")
    public String list(Model model, ProductsPageRequestDTO pageRequestDTO){
        ProductsPageResponseDTO pageResponseDTO = adminService.searchProducts(pageRequestDTO);
        model.addAttribute("page", pageResponseDTO);
        return "/admin/product/list";
    }

    @GetMapping("/admin/product/register")
    public String register(Model model){
        List<Categories> list = adminService.searchCategories();
        List<String> cateNameLists = list.stream().map(categories -> categories.getCateName()).toList();
        Set<String> cateNames = cateNameLists.stream().collect(Collectors.toSet());
        log.info(list.toString());
        model.addAttribute("cates", list);
        model.addAttribute("cateNames",cateNames);
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
      return  "redirect:/admin/product/register";
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
}
