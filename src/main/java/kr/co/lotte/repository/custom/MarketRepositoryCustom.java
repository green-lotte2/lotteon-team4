package kr.co.lotte.repository.custom;

import com.querydsl.core.Tuple;

import java.util.List;

public interface MarketRepositoryCustom {

    // market/view 페이지 product 조회
    public List<Tuple> selectProduct(int prodno);
}
