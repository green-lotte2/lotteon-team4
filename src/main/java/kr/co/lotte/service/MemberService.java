package kr.co.lotte.service;

import kr.co.lotte.dto.UserDTO;
import kr.co.lotte.entity.User;
import kr.co.lotte.mapper.MemberMapper;
import kr.co.lotte.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final MemberMapper memberMapper;
    public void insert(User user) {
        memberRepository.save(user);
    }

    public UserDTO login(UserDTO userDTO) {
        Optional<User> findUser = memberRepository.findById(userDTO.getUid());
        if (findUser.isPresent()) {

            User user = findUser.get();

            if (user.getPass().equals(userDTO.getPass())) {
                return modelMapper.map(user, UserDTO.class);
            }
            return null;
        }
        return  null;
    }

    //회원 등록이 되어 있는지 확인하는 기능(0또는 1)
    public int selectCountMember(String type, String value) {
        log.info("MemberService...........");
        int i = memberMapper.selectCountMember(type, value);
        log.info("i={}", i);
        return memberMapper.selectCountMember(type, value);
    }
}
