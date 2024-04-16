package kr.co.lotte.dto;

import kr.co.lotte.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDTO {

    private String uid;
    private String pass;
    private String name;
    private String nick;
    private String email;
    private String hp;
    private String role;
    private String zip;
    private String addr1;
    private String addr2;
    private String regip;
    private String sms;

    private String provider;
    private String grade;

    private LocalDateTime regDate;
    private LocalDateTime leaveDate;
    private int totalPoint;
    private int totalPrice;

    public User toEntity() {
        return User.builder()
                .uid(uid)
                .pass(pass)
                .name(name)
                .nick(nick)
                .email(email)
                .hp(hp)
                .role(role)
                .zip(zip)
                .addr1(addr1)
                .addr2(addr2)
                .regip(regip)
                .sms(sms)
                .provider(provider)
                .grade(grade)
                .regDate(regDate)
                .leaveDate(leaveDate)
                .totalPoint(totalPoint)
                .totalPrice(totalPrice)
                .build();
    }

}
