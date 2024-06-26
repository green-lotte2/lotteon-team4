package kr.co.lotte.repository.custom;


import com.querydsl.core.Tuple;
import kr.co.lotte.dto.ReviewPageRequestDTO;
import kr.co.lotte.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewRepositoryCustom {

    public Page<Tuple> selectReviewsAndNick(int prodno, ReviewPageRequestDTO pageRequestDTO, Pageable pageable);

    public Tuple selectForRatio(int prodno);

    public List<Tuple> selectScoreCount(int prodno);

    public Page<Review> findByUid(String uid,ReviewPageRequestDTO pageRequestDTO, Pageable pageable);

}
