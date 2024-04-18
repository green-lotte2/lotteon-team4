package kr.co.lotte.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCsArticle is a Querydsl query type for CsArticle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCsArticle extends EntityPathBase<CsArticle> {

    private static final long serialVersionUID = -593492254L;

    public static final QCsArticle csArticle = new QCsArticle("csArticle");

    public final StringPath cate = createString("cate");

    public final StringPath content = createString("content");

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final StringPath title = createString("title");

    public QCsArticle(String variable) {
        super(CsArticle.class, forVariable(variable));
    }

    public QCsArticle(Path<? extends CsArticle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCsArticle(PathMetadata metadata) {
        super(CsArticle.class, metadata);
    }

}

