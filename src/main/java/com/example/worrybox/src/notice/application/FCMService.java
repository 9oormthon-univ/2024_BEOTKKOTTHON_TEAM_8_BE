package com.example.worrybox.src.notice.application;

import com.example.worrybox.src.notice.api.dto.request.NotificationRequest;
import com.example.worrybox.src.user.domain.User;
import com.example.worrybox.src.user.domain.repository.UserRepository;
import com.example.worrybox.utils.config.BaseException;
import com.example.worrybox.utils.config.BaseResponseStatus;
import com.example.worrybox.utils.entity.Status;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FCMService {
    private final UserRepository userRepository;

    @Transactional
    public String getToken(Long userId, String token) throws BaseException {
        // 해당 아이디 가진 유저가 존재하는지 검사
        User userById = userRepository.findByIdAndStatus(userId, Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.BASE_INVALID_USER));

        userById.setFCMToken(token);
        return "토큰이 성공적으로 저장되었습니다";
    }

//    @Scheduled(cron = "0 * * * * *")
//    public void sendMessage() throws FirebaseMessagingException {
//        System.out.println("실행 되는 중");
//        List<User> users = userRepository.findAllByStatus(Status.A);
//        for(User user : users) {
//            System.out.println("Token :" + user.getId() + " " + user.getFCMToken());
//            String token = user.getFCMToken();
//
//            if(token.isEmpty()) continue;
//
//            NotificationRequest notificationRequest = new NotificationRequest(user.getFCMToken(), "FCM Test", "테스트입니다룽");
//
//            String message = FirebaseMessaging.getInstance().send(Message.builder()
//                    .setNotification(Notification.builder()
//                            .setTitle("새로운 메시지")
//                            .setBody("안녕하세요, 새로운 메시지가 도착했습니다. 진짜로?")
//                            .build())
//                    .setToken(token)  // 대상 디바이스의 등록 토큰
//                    .build());
//
//            System.out.println("Sent message: " + message);
//        }
//    }
}
