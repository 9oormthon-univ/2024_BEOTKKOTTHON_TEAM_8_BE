package com.example.worrybox.src.cheering.application;

import com.example.worrybox.src.cheering.api.dto.request.CheeringMessageRequestDto;
import com.example.worrybox.src.cheering.api.dto.response.CheeringMessageResponseDto;
import com.example.worrybox.src.cheering.domain.CheeringMessage;
import com.example.worrybox.src.cheering.domain.repository.CheeringMessageRepository;
import com.example.worrybox.src.memo.api.dto.response.MemoResponseDto;
import com.example.worrybox.src.user.domain.User;
import com.example.worrybox.src.user.domain.repository.UserRepository;
import com.example.worrybox.utils.config.BaseException;
import com.example.worrybox.utils.entity.Status;
import com.example.worrybox.utils.execption.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CheeringMessageService {
    private final CheeringMessageRepository cheeringMessageRepository;
    private final UserRepository userRepository;

    // 응원 메세지 보내기
    @Transactional
    public String sendMessage(Long userId, CheeringMessageRequestDto cheeringMessageRequestDto) throws BaseException {
        User user = userRepository.findById(userId)
                .orElseThrow(()
                        -> new EntityNotFoundException("User", new Exception("user를 찾을 수 없습니다.")));

        CheeringMessage cheeringMessage = CheeringMessage.of(user,cheeringMessageRequestDto);
        cheeringMessageRepository.save(cheeringMessage);
        return cheeringMessage.getCheeringText();
    }

    // 응원 메세지 조회
    @Transactional(readOnly = true)
    public List<CheeringMessageResponseDto> cheeringMessageList(){
        Pageable topTen = PageRequest.of(
                0, 10, Sort.by("createdAt").descending());
        // 생성일 기준 가장 최근 10개만 가져오기

        List<CheeringMessage> cheeringMessages = cheeringMessageRepository.findAll(topTen).getContent();
        List<CheeringMessageResponseDto> cheeringMessageResponseDtos = new ArrayList<>();

        for (CheeringMessage cheeringMessage :  cheeringMessages){
            if (cheeringMessage.getStatus() == Status.A) {
                CheeringMessageResponseDto cheeringMessageResponseDto = CheeringMessageResponseDto.builder()
                        .cheeringText(cheeringMessage.getCheeringText())
                        .name(cheeringMessage.getUser().getName())
                        .build();

                cheeringMessageResponseDtos.add(cheeringMessageResponseDto);
            }
        }
        return cheeringMessageResponseDtos;
    }
}
