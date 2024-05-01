package kr.co.lotte.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import kr.co.lotte.dto.*;

import kr.co.lotte.entity.*;

import kr.co.lotte.mapper.MemberMapper;
import kr.co.lotte.mapper.TermsMapper;
import kr.co.lotte.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final SellerRepository sellerRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final MemberMapper memberMapper;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final TermsMapper termsMapper;
    private final PointsRepository pointsRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewImgRepository reviewImgRepository;


    //회원 등록이 되어 있는지 확인하는 서비스(0또는 1)
    public int selectCountMember(String type, String value) {
        return memberMapper.selectCountMember(type, value);
    }

    //이메일 보내기 서비스
    @Value("${spring.mail.username}")//이메일 보내는 사람 주소
    private String sender;

    public void sendEmailCode(HttpSession session, String receiver) {
        log.info("sender={}", sender);

        //MimeMessage 생성
        MimeMessage message = javaMailSender.createMimeMessage();

        //인증코드 생성 후 세션 저장
        String code = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
        session.setAttribute("code", code);

        log.info("code={}", code);

        String title = "lotteShop 인증코드 입니다.";
        String content = "<h1>인증코드는 " + code + "입니다.<h1>";

        try {
            message.setSubject(title);
            message.setFrom(new InternetAddress(sender, "보내는 사람", "UTF-8"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject(title);
            message.setContent(content, "text/html;charset=UTF-8");

            javaMailSender.send(message);

        } catch (Exception e) {
            log.error("error={}", e.getMessage());
        }

    }

    // 회원 가입 서비스
    public void insert(UserDTO userDTO) {
        String encodedPass = passwordEncoder.encode(userDTO.getPass());

        userDTO.setPass(encodedPass);

        userDTO.setRole("USER");
        userDTO.setTotalPoint(5000);
        User user = modelMapper.map(userDTO, User.class);
        memberRepository.save(user);
        Points points = new Points();
        points.setUserId(userDTO.getUid());
        points.setPoint(5000);
        points.setPointDesc("회원가입 적립");
        points.setState("적립");

        LocalDateTime currentDate = LocalDateTime.now();
        // 날짜 증가
        LocalDateTime modifiedDate = currentDate.plusYears(1);
        points.setEndDateTime(modifiedDate);
        pointsRepository.save(points);
    }

    public void insert(SellerDTO sellerDTO) {
        String encodedPass = passwordEncoder.encode(sellerDTO.getSellerPass());

        sellerDTO.setSellerPass(encodedPass);

        Seller seller = modelMapper.map(sellerDTO, Seller.class);
        sellerRepository.save(seller);
    }

    public UserDTO login(UserDTO userDTO) {
        Optional<User> findUser = memberRepository.findById(userDTO.getUid());
        if (findUser.isPresent()) {

            User user = findUser.get();

            if (user.getPass().equals(userDTO.getPass())) {
                return modelMapper.map(user, UserDTO.class);
            }
            return null;
        }
        return null;
    }

    public UserDTO findUser(String uid) {
        return memberMapper.findUser(uid);
    }

    //terms
    public TermsDTO findTerms(int intPk) {

        return termsMapper.findTerms(intPk);
    }

    public ResponseEntity<?> myInfoUpdate(String type, String value, String uid) {
        memberMapper.updateUserForType(type, value, uid);
        Map<String, Object> map = new HashMap<>();
        map.put("success", "100");
        return ResponseEntity.ok().body(map);
    }

    public ResponseEntity<?> updateUserAddr(UserDTO userDTO) {
        memberMapper.updateUserAddr(userDTO);
        Map<String, Object> map = new HashMap<>();
        map.put("success", "100");
        return ResponseEntity.ok().body(map);
    }

    public void updateUserPassword(UserDTO userDTO) {
        String encoded = passwordEncoder.encode(userDTO.getPass());
        userDTO.setPass(encoded);

        memberMapper.updateUserPassword(userDTO);
    }

    public ResponseEntity<?> rRegister(ReviewDTO reviewDTO) {
        try {

            Review review = modelMapper.map(reviewDTO, Review.class);

            MultipartFile image1 = reviewDTO.getMultImage1();

            ReviewImgDTO uploadedImage = uploadReviewImage(image1);


            if (uploadedImage != null) {

                ReviewImgDTO imageDTO = uploadedImage;

                review.setThumbnail(uploadedImage.getSName());
            }


            log.info("service - rRegister : " + review);

            Review saveReview = reviewRepository.save(review);

            log.info("service - saveReview 저장성공?! : " + saveReview);

            int saveReviewNo = saveReview.getRno();//리뷰저장하면 리뷰번호가 자동으로 생성됨. -> 그거 불러옴

            log.info("service - saveReviewNo : " + saveReviewNo);//리뷰번호 찍어보기

            ReviewImgDTO reviewImgDTO = uploadedImage;
            reviewImgDTO.setRno(saveReviewNo);

            ReviewImg reviewImg = modelMapper.map(reviewImgDTO, ReviewImg.class);

            reviewImgRepository.save(reviewImg);
            reviewRepository.flush();

            Map<String, Object> map = new HashMap<>();
            map.put("data", saveReviewNo);//리뷰 번호임

            return ResponseEntity.ok().body(map);
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

    @Value("${file.upload.path}")
    private String fileUploadPath;

    public ReviewImgDTO uploadReviewImage(MultipartFile file) {
        // 파일을 저장할 경로 설정

        String path = new java.io.File(fileUploadPath).getAbsolutePath();

        if (!file.isEmpty()) {
            try {
                // 원본 파일 이름과 확장자 추출
                String originalFileName = file.getOriginalFilename();//원본 파일 네임
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

                log.info("uploadReviewImage - originalFileName :  잘 들어오나요? : "+originalFileName);

                // 저장될 파일 이름 생성
                String sName = UUID.randomUUID().toString() + extension;//변환된 파일 이름


                log.info("파일 변환 후 이름 - sName : "+sName);

                // 파일 저장 경로 설정
                java.io.File dest = new File(path, sName);

                Thumbnails.of(file.getInputStream())
                        .forceSize(80, 80)//여기를 size에서 forceSize로 강제 사이즈 변환
                        .toFile(dest);


                log.info("service - dest : "+ dest);

                // 리뷰이미지 정보를 담은 DTO 생성 및 반환
                return ReviewImgDTO.builder()
                        .oName(originalFileName)
                        .sName(sName)
                        .build();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null; // 업로드 실패 시 null 반환
    }
}



