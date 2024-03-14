package com.example.worrybox.src.memo.application;

import com.example.worrybox.src.memo.api.dto.request.MemoRequestDto;
import com.example.worrybox.src.memo.api.dto.response.MemoResponseDto;
import com.example.worrybox.src.memo.domain.Memo;
import com.example.worrybox.src.memo.domain.repository.MemoRepository;
import com.example.worrybox.src.user.domain.User;
import com.example.worrybox.src.user.domain.repository.UserRepository;
import com.example.worrybox.utils.config.BaseException;
import com.example.worrybox.utils.execption.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;

    // 메모 작성
    @Transactional
    public String writeMemo(Long userId, MemoRequestDto memoRequestDto) throws BaseException {
        User user = userRepository.findById(userId)
                .orElseThrow(()
                        -> new EntityNotFoundException("User", new Exception("user를 찾을 수 없습니다.")));

        Memo memo = Memo.of(user,memoRequestDto);

        memoRepository.save(memo);
        return memo.getWorryText();
    }

    // 메모 조회
    @Transactional(readOnly = true)
    public List<MemoResponseDto> memoList(Long userId){
        List<Memo> memos = memoRepository.findByUserId(userId)
                .orElseThrow(()
                        -> new EntityNotFoundException("Memo", new Exception("userId로 memo들을 찾을 수 없습니다.")));

        List<MemoResponseDto> memoResponseDtos = new ArrayList<>(); // 반환할 값

        for (Memo memo : memos) {
            MemoResponseDto memoResponseDto = MemoResponseDto.builder()
                    .createdAt(memo.getCreatedAt())
                    .worryText(memo.getWorryText())
                    .solution(memo.getSolution())
                    .build();

            memoResponseDtos.add(memoResponseDto);

        }
        return memoResponseDtos;
    }
}
