package kr.co.lotte.service.cs;

import groovy.util.logging.Log4j2;
import kr.co.lotte.dto.CsFaqPageRequestDTO;
import kr.co.lotte.dto.CsFaqPageResponseDTO;
import kr.co.lotte.dto.CsQnaDTO;
import kr.co.lotte.entity.CsQna;
import kr.co.lotte.repository.cs.CsFaqRepository;
import kr.co.lotte.repository.cs.CsQnaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CsQnaService {

    private final ModelMapper modelMapper;
    private final CsQnaRepository csQnaRepository;
    private final CsFaqRepository csFaqRepository;


    // cs.qna reg
    public void insertQna(CsQnaDTO csQnaDTO){
        // dto를 entity로 변환
        CsQna csQna = modelMapper.map(csQnaDTO, CsQna.class);
        // 매핑된 entity를 db에 저장
        CsQna savedCsQna = csQnaRepository.save(csQna);
    }
    // cs.qna list
    public List<CsQna> qnaList(){
        return csQnaRepository.findAll();
    }

    // cs.qna.list 특정 글 조회
    public CsFaqPageResponseDTO getQnaCate1andCate2(CsFaqPageRequestDTO requestDTO){
        Pageable pageable = requestDTO.getPageable("no");
        Page<CsQna> lists = csFaqRepository.searchAllCsQna(requestDTO, pageable);
        List<CsQna> dtoLists = lists.getContent();
        int total = (int) lists.getTotalElements();
        return new CsFaqPageResponseDTO(dtoLists, requestDTO, total);
    }

    // cs.qna.view 뷰페이지
    public CsQnaDTO qnaView(int no){

        CsQna csQna = csQnaRepository.findById(no).get();
        return modelMapper.map(csQna, CsQnaDTO.class);
    }

    // admin.cs.qna 대답
    public void adminQnaComment(CsQnaDTO csQnaDTO){
        // 번호로 부르기
        int no = csQnaDTO.getNo();

        CsQna csQna = modelMapper.map(csQnaDTO, CsQna.class);
        CsQna savedCsQna = csQnaRepository.save(csQna);

    }
}
