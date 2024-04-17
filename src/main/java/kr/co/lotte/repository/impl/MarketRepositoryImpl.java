package kr.co.lotte.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.lotte.entity.QProdImage;
import kr.co.lotte.entity.QProducts;
import kr.co.lotte.entity.QSeller;
import kr.co.lotte.repository.custom.MarketRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class MarketRepositoryImpl implements MarketRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QProducts qProduct = QProducts.products;
    private final QProdImage qImages = QProdImage.prodImage;
    private final QSeller qSeller = QSeller.seller;


    // 장보기 게시판 게시글 출력 (market/view)
    @Override
    public List<Tuple> selectProduct(int prodno) {
        // select * from `product` as a join `images` as b on a.prodno = b.prodno where a`prodno` = ?
        //SELECT * FROM `prodimage`AS a JOIN `products` AS b ON a.pNo = b.prodNo JOIN `seller`AS c ON b.sellerUid=c.sellerUid WHERE b.prodNo=1;
        List<Tuple> joinProduct = jpaQueryFactory
                .select(qProduct, qImages,qSeller)
                .from(qImages)
                .join(qProduct)
                .on(qProduct.prodNo.eq(qImages.pNo))
                .join(qSeller)
                .on(qProduct.sellerUid.eq(qSeller.sellerUid))
                .where(qProduct.prodNo.eq(prodno))
                .fetch();

        log.info("무슨 값들이 들어오는가? " +joinProduct);

        return joinProduct;

    }

   /*

    // market/view에서 장바구니에 품목 추가
    @Override
    @Transactional
    public Integer addProductForCart(String uid, int prodno, int prodCount){
        Integer cartNo = jpaQueryFactory
                            .select(qCart.cartNo)
                            .from(qCart)
                            .where(qCart.uid.eq(uid))
                            .fetchOne();

        List<Cart_product> result = jpaQueryFactory
                                    .selectFrom(qCart_product)
                                    .where(qCart_product.cartNo.eq(cartNo).and(qCart_product.prodNo.eq(prodno)))
                                    .fetch();

        if (result.isEmpty()){
            return cartNo;
        }else {
            return -1; // 이미 존재하는 상품
        }
    }

    */
}
