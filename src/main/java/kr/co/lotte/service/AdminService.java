package kr.co.lotte.service;

import kr.co.lotte.dto.ProdImageDTO;
import kr.co.lotte.dto.ProductsDTO;
import kr.co.lotte.dto.SubProductsDTO;
import kr.co.lotte.entity.Categories;
import kr.co.lotte.entity.ProdImage;
import kr.co.lotte.entity.Products;
import kr.co.lotte.entity.SubProducts;
import kr.co.lotte.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.PanelUI;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private ProdImageRepository prodImageRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private SubProductsRepository subProductsRepository;


    //카테고리 조회 method들
    public List<Categories> searchCategories(){
        return categoriesRepository.findAll();
    }
    public ResponseEntity searchCategoriesSecondNames(String cate){
        Set<String> names = categoriesRepository.findAllByCateName(cate).stream().map(categories -> categories.getSecondCateName()).collect(Collectors.toSet());
        List<String> namesList=names.stream().sorted().collect(Collectors.toList());
        log.info("searchCategoriesSecondNames {}!!", names);
        Map<String, List<String>> map = new HashMap<>();
        map.put("data", namesList);
        return ResponseEntity.ok().body(map);
    }

    public ResponseEntity searchCategoriesThridNames(String cate){
        Set<String> names = categoriesRepository.findAllBySecondCateName(cate).stream().map(categories -> categories.getThirdCateName()).collect(Collectors.toSet());
        List<String> namesList=names.stream().sorted().collect(Collectors.toList());
        log.info("searchCategoriesSecondNames {}!!", names);
        Map<String, List<String>> map = new HashMap<>();
        map.put("data", namesList);
        return ResponseEntity.ok().body(map);
    }

    public ResponseEntity productRegister(ProductsDTO productsDTO) {

        Products products = modelMapper.map(productsDTO, Products.class);


        MultipartFile image3 = productsDTO.getMultImage3();
        MultipartFile image4 = productsDTO.getMultImage4();

        List<MultipartFile> files = List.of( image3 , image4);

        List<ProdImageDTO> uploadedImages = fileUpload(files);
        //image1,2,3 set 해서 sname 넣기.
        if(!uploadedImages .isEmpty()){
            for (int i = 0; i < uploadedImages.size(); i++) {
                ProdImageDTO imageDTO = uploadedImages.get(i);
                if (i == 0) {
                    products.setImage1(imageDTO.getSName());
                } else if (i == 1) {
                    products.setImage2(imageDTO.getSName());
                } else if (i == 2) {
                    products.setImage3(imageDTO.getSName());
                }else{
                    products.setImage4(imageDTO.getSName());
                }
            }
        }

        log.info("registerProd....1"+ products);
        String sellerName = sellerRepository.findById(products.getSellerUid()).get().getSellerName();
        log.info("registerProd....2!!?"+ sellerName);
        products.setSellerName(sellerName);
        log.info("registerProd....!!!!"+ products);
        Products savedProduct = productsRepository.save(products);
        log.info("registerProd....2" + savedProduct);
        int saveProdNo = savedProduct.getProdNo();
        log.info("registerProd....3" + saveProdNo);
        List<SubProducts> subProducts =subProductsRepository.findAllByProdNo(0);
        log.info("registerProd....3"+subProducts);

        for(SubProducts subProducts1 : subProducts){
            subProducts1.setProdNo(saveProdNo);
            subProductsRepository.save(subProducts1);
        }
        for (ProdImageDTO prodImageDTO : uploadedImages){
            prodImageDTO.setPNo(savedProduct.getProdNo());

            ProdImage prodImage = modelMapper.map(prodImageDTO, ProdImage.class);
            prodImageRepository.save(prodImage);
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("data", savedProduct.getProdNo());
        return ResponseEntity.ok().body(map);

    }
    @Value("${file.upload.path}")
    private  String fileUploadPath;

    public List<ProdImageDTO> fileUpload(List<MultipartFile> files){

        if (fileUploadPath.startsWith("file:")) {
            fileUploadPath =  fileUploadPath.substring("file:".length());
        };

        String path = new File(fileUploadPath).getAbsolutePath();

        // 이미지 정보 리턴을 위한 리스트
        List<ProdImageDTO> imageDTOS = new ArrayList<>();

        log.info("fileUploadPath..1 : " + path);

        for (int i = 0; i < files.size(); i++) {
            MultipartFile mf = files.get(i);
            if (!mf.isEmpty()) {

                try {

                    if (i == 0) {
                        String oName = mf.getOriginalFilename();
                        String ext = oName.substring(oName.lastIndexOf(".")); // 확장자
                        String sName = UUID.randomUUID().toString() + ext;
                        File file = new File(path, sName);


                        Thumbnails.of(mf.getInputStream())
                                .size(190, 190) // 썸네일 크기 지정
                                .toFile(file);

                        ProdImageDTO prodImageDTO = ProdImageDTO.builder()
                                .oName(oName)
                                .sName(sName)
                                .build();


                         oName = mf.getOriginalFilename();
                         ext = oName.substring(oName.lastIndexOf(".")); // 확장자
                        sName = UUID.randomUUID().toString() + ext;
                        file = new File(path, sName);
                        Thumbnails.of(mf.getInputStream())
                                .size(230, 230) // 썸네일 크기 지정
                                .toFile(file);

                        ProdImageDTO prodImageDTO2 = ProdImageDTO.builder()
                                .oName(oName)
                                .sName(sName)
                                .build();


                        oName = mf.getOriginalFilename();
                        ext = oName.substring(oName.lastIndexOf(".")); // 확장자
                        sName = UUID.randomUUID().toString() + ext;
                        file = new File(path, sName);
                        Thumbnails.of(mf.getInputStream())
                                .size(456, 456) // 썸네일 크기 지정
                                .toFile(file);

                        ProdImageDTO prodImageDTO3 = ProdImageDTO.builder()
                                .oName(oName)
                                .sName(sName)
                                .build();

                        imageDTOS.add(prodImageDTO);
                        imageDTOS.add(prodImageDTO2);
                        imageDTOS.add(prodImageDTO3);

                    } else {
                        String oName = mf.getOriginalFilename();
                        String ext = oName.substring(oName.lastIndexOf(".")); // 확장자
                        String sName = UUID.randomUUID().toString() + ext;
                        File file = new File(path, sName);

                        Thumbnails.of(mf.getInputStream())
                                .size(940, 940) // 썸네일 크기 지정
                                .toFile(file);

                        ProdImageDTO prodImageDTO = ProdImageDTO.builder()
                                .oName(oName)
                                .sName(sName)
                                .build();

                        imageDTOS.add(prodImageDTO);
                    }

                    // 파일 정보 생성(imageDB에 저장될 DTO)
                } catch (IOException e) {
                    log.error("Failed to upload file: " + e.getMessage());
                }
            }
        }

        return imageDTOS;
    }

    public ResponseEntity insertSubOptions(List<SubProductsDTO> subProductsDTOS){
        for(SubProductsDTO subProductsDTO : subProductsDTOS){
            subProductsRepository.save(modelMapper.map(subProductsDTO, SubProducts.class));
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("data", 1);
        return ResponseEntity.ok().body(map);

    }
    public List<Products> searchProducts(){
        return productsRepository.findAll();
    }
}
