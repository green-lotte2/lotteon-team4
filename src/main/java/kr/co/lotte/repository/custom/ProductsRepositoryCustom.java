package kr.co.lotte.repository.custom;

import com.querydsl.core.Tuple;
import kr.co.lotte.dto.ProductsPageRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepositoryCustom  {

    public Page<Tuple> searchAllProductsForAdmin(ProductsPageRequestDTO pageRequestDTO, Pageable pageable);
}
