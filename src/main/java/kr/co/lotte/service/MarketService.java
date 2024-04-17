package kr.co.lotte.service;


import com.querydsl.core.Tuple;
import kr.co.lotte.dto.ProdImageDTO;
import kr.co.lotte.dto.ProductsDTO;
import kr.co.lotte.dto.SellerDTO;
import kr.co.lotte.entity.ProdImage;
import kr.co.lotte.entity.Products;
import kr.co.lotte.entity.Seller;
import kr.co.lotte.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MarketService {

    private final MarketRepository marketRepository;
    private final ModelMapper modelMapper;

    // 장보기 글보기 페이지 - 장보기 게시글 출력
    public ProductsDTO selectProduct(int prodno){
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


                            productDTO.setImage1(imagesDTO.getImage240());
                            productDTO.setImage2(imagesDTO.getImage750());
                            productDTO.setSellerName(sellerDTO.getSellerName());
                            return productDTO;
                        }
                )
                .findFirst()
                .orElse(null);


        log.info("joinProductDTO-marketService: "+joinProductDTO);
        return joinProductDTO;
    }

}
