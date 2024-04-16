package kr.co.lotte.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "prodimage")
public class ProdImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iNo;
    private int pNo;
    private String oName;
    private String sName;


}