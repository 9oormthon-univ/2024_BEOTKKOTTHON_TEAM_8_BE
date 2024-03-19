package com.example.worrybox.src.cloud.application;

import com.example.worrybox.src.memo.domain.repository.MemoRepository;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CloudWordService {
    private final MemoRepository memoRepository;
    Komoran komoran = new Komoran(DEFAULT_MODEL.FULL); // 모델 경로를 지정해야 합니다.
    public List<String> setKomoran(){
        String text = "KOMORAN은 자바로 구현된 한국어 형태소 분석기입니다.";
        KomoranResult result = komoran.analyze(text);
        return result.getNouns();
    }


    // 분석할 텍스트입니다.


    // 텍스트를 분석합니다.



}
