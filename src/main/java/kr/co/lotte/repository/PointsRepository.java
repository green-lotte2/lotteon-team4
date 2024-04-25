package kr.co.lotte.repository;


import kr.co.lotte.entity.Banner;
import kr.co.lotte.entity.Points;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointsRepository extends JpaRepository<Points, Integer> {

}