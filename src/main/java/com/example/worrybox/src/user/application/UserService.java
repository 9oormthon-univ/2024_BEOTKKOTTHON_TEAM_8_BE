package com.example.worrybox.src.user.application;

import com.example.worrybox.src.user.api.dto.request.PostJoinReq;
import com.example.worrybox.src.user.api.dto.request.PostLoginReq;
import com.example.worrybox.src.user.api.dto.response.PostLoginRes;
import com.example.worrybox.src.user.api.dto.response.PostUserRes;
import com.example.worrybox.src.user.domain.User;
import com.example.worrybox.src.user.domain.repository.UserRepository;
import com.example.worrybox.utils.config.BaseException;
import com.example.worrybox.utils.config.BaseResponseStatus;
import com.example.worrybox.utils.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public String nameCheck(String name) throws BaseException {
        // 해당 닉네임을 가진 유저가 존재하는지 검사
        Optional<User> userByName = userRepository.findByNameAndStatus(name, Status.A);

        if(userByName.isPresent()) {  // 해당 이름이 있는 경우
            throw new BaseException(BaseResponseStatus.JOIN_INVALID_NAME);
        } else {  // 해당 이름이 없는 경우 -> 가능하다는 메세지 반환
            return "회원가입이 가능합니다.";
        }
    }

    @Transactional
    public PostUserRes join(PostJoinReq userReq) throws BaseException {
        System.out.println(userReq.getName());
        String name = userReq.getName();

        Optional<User> userByName = userRepository.findByNameAndStatus(name, Status.A);

        if(userByName.isPresent()) {  // 해당 이름이 있는 경우
            throw new BaseException(BaseResponseStatus.JOIN_INVALID_NAME);
        } else {  // 해당 이름이 없는 경우 -> 새로 회원가입 진행
            User newUser = userRepository.save(User.of(userReq));
            return new PostUserRes(newUser.getId());
        }
    }

    @Transactional
    public PostLoginRes login(PostLoginReq userReq) throws BaseException {
        String name = userReq.getName();
        int password = userReq.getPassword();

        Optional<User> userByName = userRepository.findByNameAndPasswordAndStatus(name, password, Status.A);
        if(userByName.isPresent()) {  // 해당 계정이 있는 경우 로그인 진행
            User existingUser = userByName.get();
            Long id = existingUser.getId();
            String startTime = existingUser.getWorry_start_time(), endTime = existingUser.getWorry_end_time();

            return new PostLoginRes(id, startTime, endTime);
        } else {  // 존재하지 않는 유저 에러 발생
            throw new BaseException(BaseResponseStatus.BASE_INVALID_USER);
        }
    }

}
