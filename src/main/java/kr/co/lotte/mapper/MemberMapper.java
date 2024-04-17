package kr.co.lotte.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {

    public int selectCountMember(@Param("type") String type, @Param("value") String value);
}
