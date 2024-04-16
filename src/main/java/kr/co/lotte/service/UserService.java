package kr.co.lotte.service;

import kr.co.lotte.entity.User;
import kr.co.lotte.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public void register (User user) {
        repository.save(user);
    }
}
