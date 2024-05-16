package kr.co.lotte.dto;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CsFaqPageRequestDTO {

    @Builder.Default
    private  int no =1;

    @Builder.Default
    private  int pg=1;

    @Builder.Default
    private  int size =10;

  private String cate1;
  private String cate2;
  private String cate;


  private String group;

    public Pageable getPageable(String sort){
        return PageRequest.of(this.pg - 1, this.size, Sort.by(sort).descending());
    }
}