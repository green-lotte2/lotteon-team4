package kr.co.lotte.service;

import kr.co.lotte.dto.UserDTO;
import kr.co.lotte.entity.User;
import kr.co.lotte.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    ModelMapper modelMapper = new ModelMapper();

    public void insert(User user) {
        repository.save(user);
    }

    public UserDTO login(UserDTO userDTO) {
        Optional<User> findUser = repository.findById(userDTO.getUid());
        if (findUser.isPresent()) {

            User user = findUser.get();

            if (user.getPass().equals(userDTO.getPass())) {
                return modelMapper.map(user, UserDTO.class);
            }
            return null;
        }
        return  null;
    }
}
