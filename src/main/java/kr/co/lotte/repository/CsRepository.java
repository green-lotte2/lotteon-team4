package kr.co.lotte.repository;

import kr.co.lotte.entity.CsFaq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CsRepository extends JpaRepository<CsFaq, Integer> {
    List<CsFaq> findByCate(String cate);
}
