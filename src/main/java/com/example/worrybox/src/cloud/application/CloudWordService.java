package com.example.worrybox.src.cloud.application;

import com.example.worrybox.src.memo.api.dto.response.MemoResponseDto;
import com.example.worrybox.src.memo.domain.Memo;
import com.example.worrybox.src.memo.domain.repository.MemoRepository;
import com.example.worrybox.src.user.domain.User;
import com.example.worrybox.src.user.domain.repository.UserRepository;
import com.example.worrybox.utils.entity.Status;
import com.example.worrybox.utils.execption.EntityNotFoundException;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, Integer> getWordCloud_2(Long userId) {
        List<Memo> memos = memoRepository.findByUserId(userId);

        if (memos.isEmpty()) {
            throw new EntityNotFoundException("Memo", new Exception("userId로 memo들을 찾을 수 없습니다."));
        }

        Map<String, Integer> wordCounts = new HashMap<>(); // 명사들의 출현 빈도를 저장할 맵

        List<String> words = new ArrayList<>(); // 명사들만을 포함할 리스트

        for (Memo memo : memos) {
            KomoranResult result = komoran.analyze(memo.getWorryText()); // 메모 분석
            List<String> nouns = result.getNouns(); // 현재 메모에서 추출된 명사들

            for (String noun : nouns) {
                wordCounts.put(noun, wordCounts.getOrDefault(noun, 0) + 1); // 맵에 명사와 출현 횟수를 저장
            }

            words.addAll(nouns); // 전체 명사 리스트에 추가
        }

        return wordCounts; // 명사들만 포함된 리스트 반환
    }
}
