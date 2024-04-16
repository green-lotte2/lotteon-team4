package kr.co.lotte.service;

import kr.co.lotte.entity.Categories;
import kr.co.lotte.repository.CategoriesRepository;
import kr.co.lotte.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {
    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductsRepository productsRepository;

    public List<Categories> searchCategories(){
        return categoriesRepository.findAll();
    }
}
