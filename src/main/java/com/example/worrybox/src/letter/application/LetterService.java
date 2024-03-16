package com.example.worrybox.src.letter.application;

import com.example.worrybox.src.letter.api.dto.request.PostLetterReq;
import com.example.worrybox.src.letter.domain.Letter;
import com.example.worrybox.src.letter.domain.LetterRepository;
import com.example.worrybox.src.user.domain.User;
import com.example.worrybox.src.user.domain.repository.UserRepository;
import com.example.worrybox.utils.config.BaseException;
import com.example.worrybox.utils.config.BaseResponseStatus;
import com.example.worrybox.utils.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LetterService {
    private final UserRepository userRepository;
    private final LetterRepository letterRepository;

    @Transactional
    public Long sendLetter(Long userId, PostLetterReq letterReq) throws BaseException, ParseException {
        Optional<User> userById = userRepository.findByIdAndStatus(userId, Status.A);
        if(userById.isEmpty()) {  // 존재하지 않는 유저 에러 발생
            throw new BaseException(BaseResponseStatus.BASE_INVALID_USER);
        }

//        for(Letter letter : letterRepository.findAll()) {
//            System.out.println(letter.getUser());
//            System.out.println(letter.getLetter_text());
//            System.out.println(letter.getArrival_date());
//        }

        User user = userById.get();
        String letter = letterReq.getLetter(), arrivalTime = letterReq.getArrivalDate();
        Letter newLetter = letterRepository.save(Letter.of(user, letter, getDate(arrivalTime)));

        return newLetter.getId();
    }

    public Date getDate(String time) throws ParseException {
         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
         Date date = formatter.parse(time);
         System.out.println(date);

         return date;
    }

//    public List<> getLetter(Long userId) throws BaseException {
//        Optional<User> userById = userRepository.findByIdAndStatus(userId, Status.A);
//        if(userById.isEmpty()) {  // 존재하지 않는 유저 에러 발생
//            throw new BaseException(BaseResponseStatus.BASE_INVALID_USER);
//        }
//
//
//        return newLetter.getId();
//    }
}
