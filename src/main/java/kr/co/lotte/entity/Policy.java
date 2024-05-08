package kr.co.lotte.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access =AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name="policy")

public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //의미없는 pk값
    private String name; // state를 나타내는걸로 구매 / 판매 / 전자/ 위치/ 개인
    private String chapter1;//약관
    private String chapter2;//약관
    private String chapter3;//약관
    private String chapter4;//약관
    private String chapter5;//약관
    private String chapter6;//약관
}