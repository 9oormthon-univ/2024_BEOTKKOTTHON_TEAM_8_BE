package com.example.worrybox.src.cloud.application;

import com.example.worrybox.src.cloud.api.dto.CloudResponseDto;
import com.example.worrybox.src.memo.domain.Memo;
import com.example.worrybox.src.memo.domain.repository.MemoRepository;
import com.example.worrybox.src.user.domain.User;
import com.example.worrybox.src.user.domain.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map.Entry;
import com.example.worrybox.utils.execption.EntityNotFoundException;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

// 워드 클라우드 명사 상위 n개 ,
// 가장 많이 메모 쓴 날짜,
// 가장 많이 걱정한 시간,
// 날짜는 3월로 넣기(즉 그냥 아무 손 보지 말고 넣어라).
// 발표 당일 (3월 24일) 까지의 메모들 기준으로 값을 반환.
// 최대 50개만
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CloudWordService {
    private final MemoRepository memoRepository;
    private final UserRepository userRepository;
    private final ChatClient chatClient;
    private final Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);

    // 워드 클라우드 명사들 나열
    public List<String> getWordCloud_1(Long userId) {
        List<Memo> memos = memoRepository.findByUserId(userId);

        if (memos.isEmpty()) {
            throw new EntityNotFoundException("Memo", new Exception("userId로 memo들을 찾을 수 없습니다."));
        }

        List<String> words = new ArrayList<>(); // 명사들만을 포함할 리스트

        for (Memo memo : memos) {
            KomoranResult result = komoran.analyze(memo.getWorryText()); // 메모 분석
            List<String> nouns = result.getNouns(); // 현재 메모에서 추출된 명사들

            words.addAll(nouns); // 전체 명사 리스트에 추가
        }

        return words; // 명사들만 포함된 리스트 반환
    }

    // 워드 클라우드 명사들 나열이 아닌 명사 개수 반환
    public CloudResponseDto getWordCloud_2(Long userId) {
        List<Memo> memos = memoRepository.findByUserId(userId);

        if (memos.isEmpty()) {
            throw new EntityNotFoundException("Memo", new Exception("userId로 memo들을 찾을 수 없습니다."));
        }

        Map<String, Integer> wordCounts = new HashMap<>(); // 빈도를 저장할 맵

        for (Memo memo : memos) {
            KomoranResult result = komoran.analyze(memo.getWorryText()); // 메모 분석
            List<String> nouns = result.getNouns(); // 현재 메모에서 추출된 명사들

            for (String noun : nouns) {
                wordCounts.put(noun, wordCounts.getOrDefault(noun, 0) + 1); // 맵에 명사와 출현 횟수를 저장
            }
        }

        // 명사들의 출현 빈도를 포함하는 맵을 주어진 형식의 리스트로 변환, 리스트 형식으로 넣어줘야하니까 따로 작업 필요..
        List<Map<String, Object>> wordsList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
            if (wordsList.size() >= 50) {
                break; // 최대 50개로 워드 클라우드 생성
            }
            Map<String, Object> wordMap = new HashMap<>();
            wordMap.put("text", entry.getKey());
            wordMap.put("value", entry.getValue());
            wordsList.add(wordMap);
        }
        // 내림 차순으로 정렬
        List<Map<String,
                Object>> sortedWordsList = wordsList.stream()
                .sorted((map1, map2) -> Integer.compare((Integer) map2.get("value"), (Integer) map1.get("value")))
                .toList();

        String maxEntry = importTime(userId); // 걱정을 가장 많은 날짜
        ChatResponse answer = callChat(sortedWordsList.get(0).get("text").toString()); // ai로 가장 많은 키워드 답변

        return CloudResponseDto.create(
                sortedWordsList,
                maxEntry,
                answer.getResult().getOutput().getContent());
    }

    // 가장 많은 메모를 넣은 날짜 반환
    private String importTime(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User", new Exception("user를 찾을 수 없습니다.")));

        List<Memo> memos = user.getMemos(); // 모든 메모 가져오기

        // 메모가 비어있으면
        if (memos.isEmpty()) {
            throw new EntityNotFoundException("Memo", new Exception("메모를 찾을 수 없습니다."));
        }

        // 날짜별(년-월-일만 고려) 메모 개수 계산
        Map<LocalDate, Integer> dateCounts = new HashMap<>();
        for (Memo memo : memos) {
            LocalDateTime dateTime = memo.getCreatedAt().toLocalDateTime(); // Timestamp를 LocalDateTime으로 변환
            LocalDate date = dateTime.toLocalDate(); // LocalDateTime에서 LocalDate 추출
            dateCounts.put(date, dateCounts.getOrDefault(date, 0) + 1);
        }

        // 가장 많이 메모가 생성된 날짜 찾기
        Optional<Entry<LocalDate, Integer>> maxEntry = dateCounts.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());

        // '년-월-일' 형식으로 변환하여 반환
        return maxEntry.map(entry -> entry.getKey().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .orElse("가장 많은 메모가 생성된 날짜를 찾을 수 없습니다.");
    }

    // AI 응답 메서드
    private ChatResponse callChat(String question) {
        return chatClient.call(
                new Prompt(
                        (question +
                                "이 너무 걱정돼. " +
                                "해결책 33자로 줘." +
                                " 반말로."),
                        OpenAiChatOptions.builder()
                                .withTemperature(0.4F)
                                .withFrequencyPenalty(0.7F)
                                .withModel("gpt-3.5-turbo")
                                .build()
                ));
    }
}