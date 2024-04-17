package kr.co.lotte.repository;


import kr.co.lotte.entity.Products;
import kr.co.lotte.repository.custom.MarketRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketRepository extends JpaRepository<Products,Integer>, MarketRepositoryCustom {
}
