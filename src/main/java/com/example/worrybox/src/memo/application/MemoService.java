package com.example.worrybox.src.memo.application;

import com.example.worrybox.src.memo.api.dto.request.AiRequestDto;
import com.example.worrybox.src.memo.api.dto.request.MemoRequestDto;
import com.example.worrybox.src.memo.api.dto.response.AiResponseDto;
import com.example.worrybox.src.memo.api.dto.response.CountResponseDto;
import com.example.worrybox.src.memo.api.dto.response.MemoResponseDto;
import com.example.worrybox.src.memo.api.dto.response.TimeResponseDto;
import com.example.worrybox.src.memo.domain.Memo;
import com.example.worrybox.src.memo.domain.repository.MemoRepository;
import com.example.worrybox.src.user.domain.User;
import com.example.worrybox.src.user.domain.repository.UserRepository;
import com.example.worrybox.utils.config.BaseException;
import com.example.worrybox.utils.entity.Status;
import com.example.worrybox.utils.execption.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;
    private final ChatClient chatClient;

    // 메모 작성
    @Transactional
    public MemoResponseDto writeMemo(Long userId, MemoRequestDto memoRequestDto) throws BaseException {
        User user = userRepository.findById(userId)
                .orElseThrow(()
                        -> new EntityNotFoundException("User", new Exception("user를 찾을 수 없습니다.")));

        Memo memo = Memo.of(user, memoRequestDto);
        memoRepository.save(memo);
        return MemoResponseDto.from(memo);
    }

    // 메모 조회
    @Transactional(readOnly = true)
    public List<MemoResponseDto> memoList(Long userId) {
        List<Memo> memos = memoRepository.findByUserId(userId);

        if (memos.isEmpty()) {
            throw new EntityNotFoundException("Memo", new Exception("userId로 memo들을 찾을 수 없습니다."));
        }

        List<MemoResponseDto> memoResponseDtos = new ArrayList<>(); // 반환할 값

        for (Memo memo : memos) {
            if (memo.getStatus() == Status.A) {
                MemoResponseDto memoResponseDto = MemoResponseDto.builder()
                        .createAt(memo.getCreatedAt())
                        .updateAt(memo.getUpdatedAt())
                        .worryText(memo.getWorryText())
                        .solution(memo.getSolution())
                        .memoId(memo.getId())
                        .build();

                memoResponseDtos.add(memoResponseDto);
            }
        }
        // updateAt 기준으로 오름차순 정렬
        memoResponseDtos.sort(Comparator.comparing(MemoResponseDto::getUpdateAt));
        return memoResponseDtos;
    }

    // 메모 삭제
    @Transactional
    public Status deleteMemo(Long memoId) {
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(()
                        -> new EntityNotFoundException("Memo", new Exception("memo를 찾을 수 없습니다.")));

        memo.updateStatus(Status.D);
        return memo.getStatus();
    }


    // AI에게 조언 구하기
    @Transactional
    public AiResponseDto askForAdvice(AiRequestDto aiRequestDto){
        ChatResponse response = callChat(aiRequestDto.getQuestion());
        if (response == null) {
            response = callChat(aiRequestDto.getQuestion());
        }

        return AiResponseDto.builder()
                .answer(response.getResult().getOutput().getContent()).build();

    }

    // AI 응답 메서드
    private ChatResponse callChat(String question) {
        return chatClient.call(
                new Prompt(
                        (question +
                                ". 너는 조언가야. " +
                                "지금 걱정에 대해 공감하고 해결책을 반말로 친구처럼 조언해줘." +
                                "50자 이하로 말해줘. " +
                                "총 3문장 정도로 말해줘."),
                        OpenAiChatOptions.builder()
                                .withTemperature(0.4F)
                                .withFrequencyPenalty(0.7F)
                                .withModel("gpt-3.5-turbo")
                                .build()
                ));
    }

    // 시간 늘려주기
    @Transactional
    public TimeResponseDto extendTime(Long memoId){
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(()
                        -> new EntityNotFoundException("Memo", new Exception("userId로 memo들을 찾을 수 없습니다.")));
        if(memo.getStatus()==Status.D){
            throw new EntityNotFoundException("Memo", new Exception("삭제된 메모입니다."));
        }
        TimeResponseDto timeResponseDto = TimeResponseDto.builder()
                .updatedAt(Timestamp.from(Instant.now()))
                .build();

        memo.updateTime(timeResponseDto.getUpdatedAt());
        return timeResponseDto;
    }

    // 3일 지난 걱정 메모 개수
    public CountResponseDto count(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()
                        -> new EntityNotFoundException("User", new Exception("user를 찾을 수 없습니다.")));
        Timestamp threeDaysAgo = Timestamp.
                valueOf(LocalDateTime.now().minusMinutes(1)); // 시간 입력 ex) 1분 지나면 count 됨.
        List<Memo> memos = memoRepository.findByUpdatedAtBeforeAndStatusAndUser(threeDaysAgo, Status.A, user);
        int count = memos.size();
        return CountResponseDto.createCount(count);
    }
}
