package kr.co.lotte.dto;

import kr.co.lotte.entity.CsArticle;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString @Builder
public class CsArticleDTO {

    private int no;
    private String cate;
    private String title;
    private String content;

    public CsArticle toEntity(){
        CsArticle article = new CsArticle();
        article.setNo(no);
        article.setCate(cate);
        article.setTitle(title);
        article.setContent(content);

        return article;
    }

}
