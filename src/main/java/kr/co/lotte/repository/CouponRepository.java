package kr.co.lotte.repository;

import kr.co.lotte.entity.BannerImg;
import kr.co.lotte.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Integer>{
   /*
     public List<Coupon> findFutureCoupons(List<Coupon> allCoupons) {
        LocalDate currentDate = LocalDate.now();

        List<Coupon> futureCoupons = allCoupons.stream()
            .filter(coupon -> LocalDate.parse(coupon.getEndDate()).compareTo(currentDate) >= 0)
            .collect(Collectors.toList());

        return futureCoupons;
    }
    이렇게 해보래~
    */

}
