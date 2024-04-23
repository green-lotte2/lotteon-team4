package kr.co.lotte.repository;

import kr.co.lotte.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
}