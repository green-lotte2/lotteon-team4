package kr.co.lotte.controller;


import kr.co.lotte.dto.ProductsDTO;
import kr.co.lotte.entity.Categories;
import kr.co.lotte.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    public String info(){
        return "/admin/config/info";
    }

    //product
    @GetMapping("/admin/product/list")
    public String list(){
        return "/admin/product/list";
    }

    @GetMapping("/admin/product/register")
    public String register(Model model){
        List<Categories> list = adminService.searchCategories();
        log.info(list.toString());
        model.addAttribute("cates", list);
        return "/admin/product/register";
    }

    @ResponseBody
    @PostMapping("/admin/product/register")
    public void register(@RequestBody ProductsDTO productsDTO){

    }

}
