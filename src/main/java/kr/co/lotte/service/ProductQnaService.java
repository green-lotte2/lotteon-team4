package kr.co.lotte.service;

import kr.co.lotte.dto.CsFaqPageRequestDTO;
import kr.co.lotte.dto.CsFaqPageResponseDTO;
import kr.co.lotte.entity.CsQna;
import kr.co.lotte.entity.ProductQna;
import kr.co.lotte.repository.ProductQnaRepository;
import kr.co.lotte.repository.cs.CsFaqRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductQnaService {

    private final ProductQnaRepository productQnaRepository;
    private final CsFaqRepository csFaqRepository;

    public List<ProductQna> productQnas(){
        return productQnaRepository.findAll();
    }

    public CsFaqPageResponseDTO getProdQnaCate(CsFaqPageRequestDTO requestDTO){
        Pageable pageable = requestDTO.getPageable("no");
        Page<ProductQna> lists = csFaqRepository.searchAllProdQna(requestDTO, pageable);
        List<ProductQna> dtoLists = lists.getContent();
        int total = (int) lists.getTotalElements();
        return new CsFaqPageResponseDTO(dtoLists, total, requestDTO);
    }

    public CsFaqPageResponseDTO getProdQnaCate(CsFaqPageRequestDTO requestDTO, String uid){
        Pageable pageable = requestDTO.getPageable("no");
        Page<ProductQna> lists = csFaqRepository.searchAllProdQna(requestDTO, pageable, uid);
        List<ProductQna> dtoLists = lists.getContent();
        int total = (int) lists.getTotalElements();
        return new CsFaqPageResponseDTO(dtoLists, total, requestDTO);
    }
}
