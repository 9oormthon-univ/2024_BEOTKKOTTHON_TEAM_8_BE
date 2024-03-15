package com.example.worrybox.src.user.application;

import com.example.worrybox.src.user.api.dto.request.PostJoinReq;
import com.example.worrybox.src.user.api.dto.request.PostLoginReq;
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
    public PostUserRes join(PostJoinReq userReq) throws BaseException {
        System.out.println(userReq.getName());
        String name = userReq.getName();

        Optional<User> userByName = userRepository.findByNameAndStatus(name, Status.A);

        if(userByName.isPresent()) {  // 해당 이름이 있는 경우
            User existingUser = userByName.get();
            // 비밀번호가 일치하는지 확인
            if(userReq.getPassword() == existingUser.getPassword()) {  // 일치하면 이미 가입한 회원 에러 발생
                throw new BaseException(BaseResponseStatus.JOIN_EXIST_USER);
            } else {  // 일치하지 않으면 중복 이름 에러 발생
                throw new BaseException(BaseResponseStatus.JOIN_INVALID_NAME);
            }
        } else {  // 해당 이름이 없는 경우 -> 새로 회원가입 진행
            User newUser = userRepository.save(User.of(userReq));
            return new PostUserRes(newUser.getId());
        }
    }

    @Transactional
    public PostUserRes login(PostLoginReq userReq) throws BaseException {
        String name = userReq.getName();
        int password = userReq.getPassword();

        Optional<User> userByName = userRepository.findByNameAndPasswordAndStatus(name, password, Status.A);
        if(userByName.isPresent()) {  // 해당 계정이 있는 경우 회원가입 진행
            User existingUser = userByName.get();
            return new PostUserRes(existingUser.getId());
        } else {  // 존재하지 않는 유저 에러 발생
            throw new BaseException(BaseResponseStatus.BASE_INVALID_USER);
        }
    }
//
//    @Transactional
//    public Long timeSetting(Long userId, PostLoginReq timeReq) throws BaseException {
//        String startTime = timeReq.getStartTime();
//        String endTime = timeReq.getEndTime();
//
//        Optional<User> userById = userRepository.findByIdAndStatus(userId, Status.A);
//        if(userById.isEmpty()) throw new BaseException(BaseResponseStatus.BASE_INVALID_USER);
//
//        User user = userById.get();
//        user.setWorry_start_time(startTime);
//        user.setWorry_end_time(endTime);
//
//        return user.getId();
//    }
}
