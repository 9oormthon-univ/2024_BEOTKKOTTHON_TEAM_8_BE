package com.example.worrybox.src.notice.application;

import com.example.worrybox.src.letter.domain.repository.GetLetterId;
import com.example.worrybox.src.letter.domain.repository.LetterRepository;
import com.example.worrybox.src.user.domain.User;
import com.example.worrybox.src.user.domain.repository.UserRepository;
import com.example.worrybox.utils.config.BaseException;
import com.example.worrybox.utils.config.BaseResponseStatus;
import com.example.worrybox.utils.entity.Status;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FCMService {
    private final UserRepository userRepository;
    private final LetterRepository letterRepository;

    @Transactional
    public String getToken(Long userId, String token) throws BaseException {
        // 해당 아이디 가진 유저가 존재하는지 검사
        User userById = userRepository.findByIdAndStatus(userId, Status.A)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.BASE_INVALID_USER));

        userById.setFCMToken(token);
        return "토큰이 성공적으로 저장되었습니다";
    }

    @Scheduled(cron = "0 0 0 * * *")
//    @Scheduled(cron = "0 * * * * *")
    public void sendLetterNotice() throws FirebaseMessagingException {
        List<User> users = userRepository.findAllByStatus(Status.A);
        for(User user : users) {
            System.out.println("Token :" + user.getId() + " " + user.getFCMToken());
            String token = user.getFCMToken();

            if(token == null) {
                continue;
            }

            List<GetLetterId> letters = letterRepository.findTodayLetters(user.getId(), getDate());
            if(!letters.isEmpty()) {
                String title = "오늘의 " + user.getName() + "에게 편지가 도착했어";
                String body = "걱정 보관함으로 와줘 !";

                sendMessage(token, title, body);
            }
        }
    }

    public String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();

        String date = formatter.format(today);
        System.out.println(date);

        return date;
    }

    public void sendMessage(String token, String title, String body) throws FirebaseMessagingException {
        String message = FirebaseMessaging.getInstance().send(Message.builder()
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .setToken(token)  // 대상 디바이스의 등록 토큰
                .build());

        System.out.println("Sent message: " + message);
    }
}
