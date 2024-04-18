package kr.co.lotte.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.core.Tuple;
import kr.co.lotte.dto.ProductsPageRequestDTO;
import kr.co.lotte.entity.Products;
import kr.co.lotte.entity.QProducts;
import kr.co.lotte.entity.QSubProducts;
import kr.co.lotte.entity.SubProducts;
import kr.co.lotte.repository.custom.ProductsRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ProductsRepositoryImpl implements ProductsRepositoryCustom {
    private  QProducts qProducts = QProducts.products;
    private  QSubProducts subProducts = QSubProducts.subProducts;

    @Autowired
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<Tuple> searchAllProductsForAdmin(ProductsPageRequestDTO pageRequestDTO, Pageable pageable) {
        QueryResults<com.querydsl.core.Tuple> results = jpaQueryFactory.select(qProducts, subProducts)
                .from(subProducts)
                .join(qProducts)
                .on(qProducts.prodNo.eq(subProducts.prodNo))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Tuple> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
