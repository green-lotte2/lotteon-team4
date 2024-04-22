package kr.co.lotte.service;


import com.querydsl.core.Tuple;
import kr.co.lotte.dto.ProdImageDTO;
import kr.co.lotte.dto.ProductsDTO;
import kr.co.lotte.dto.SellerDTO;
import kr.co.lotte.entity.ProdImage;
import kr.co.lotte.entity.Products;
import kr.co.lotte.entity.Seller;
import kr.co.lotte.entity.SubProducts;
import kr.co.lotte.repository.ProductsRepository;
import kr.co.lotte.repository.SubProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MarketService {

    private final ProductsRepository marketRepository;
    private final ModelMapper modelMapper;
    private final SubProductsRepository subProductsRepository;
    private final ProductsRepository productRepository;

    // 장보기 글보기 페이지 - 장보기 게시글 출력
    public ProductsDTO selectProduct(int prodno){

        log.info("prodno : "+prodno);


        List<Tuple> joinProduct = marketRepository.selectProduct(prodno);
        // List에서 Product, Images 엔티티 꺼낸 후 ProductDTO로 병합
        ProductsDTO joinProductDTO = joinProduct.stream()
                .map(tuple ->
                        {
                            Products product = tuple.get(0, Products.class);
                            ProdImage images = tuple.get(1, ProdImage.class);
                            Seller seller = tuple.get(2, Seller.class);


                            ProductsDTO productDTO = modelMapper.map(product, ProductsDTO.class);
                            ProdImageDTO imagesDTO = modelMapper.map(images, ProdImageDTO.class);
                            SellerDTO sellerDTO = modelMapper.map(seller, SellerDTO.class);


                            productDTO.setImage1(imagesDTO.getOName());
                            productDTO.setImage2(imagesDTO.getSName());
                            productDTO.setSellerName(sellerDTO.getSellerName());
                            return productDTO;
                        }
                )
                .findFirst()
                .orElse(null);


        log.info("joinProductDTO-marketService: "+joinProductDTO);
        return joinProductDTO;
    }


    public List<SubProducts> findAllByProdNo(int prodno){

        //prodno의 모든 데이터를 출력
        List<SubProducts> Options = subProductsRepository.findAllByProdNo(prodno);

        return Options;
    }

}
