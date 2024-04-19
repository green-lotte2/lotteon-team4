package kr.co.lotte.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
@Entity
@Table(name="terms")

public class Terms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int intPk;
    private String terms;
    private String terms2;
    private String privacy;
    private String sms;
}