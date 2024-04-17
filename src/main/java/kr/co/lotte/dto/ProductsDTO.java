package kr.co.lotte.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductsDTO {
    private int prodNo;
    private int cateNo;
    private String prodName;
    private int prodPrice;
    private int prodStock;
    private int prodSold; //판매액
    private int prodDiscount;

    private List<String> detail1;
    private List<String> detail2;

    private MultipartFile multImage1;
    private MultipartFile multImage2;
    private MultipartFile multImage3;

    private String image1;
    private String image2;
    private String image3;
    private int recount;
    private int point;
    private String etc;
    private int delivery;
    private LocalDateTime RegProdDate;

    private String cateName;

    private String sellerName;

}
