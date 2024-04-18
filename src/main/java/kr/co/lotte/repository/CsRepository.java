package kr.co.lotte.repository;

import kr.co.lotte.entity.CsArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CsRepository extends JpaRepository<CsArticle, Integer> {
    List<CsArticle> findByCate(String cate);
}
