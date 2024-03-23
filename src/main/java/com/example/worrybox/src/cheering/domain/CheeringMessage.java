package com.example.worrybox.src.cheering.domain;

import com.example.worrybox.src.cheering.api.dto.request.CheeringMessageRequestDto;
import com.example.worrybox.src.user.domain.User;
import com.example.worrybox.utils.entity.BaseEntity;
import com.example.worrybox.utils.entity.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor(access= AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheeringMessage extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    private String cheeringText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 정적 팩토링 메서드
    public static CheeringMessage of(User user, CheeringMessageRequestDto cheeringMessageRequestDto){
        return CheeringMessage.builder()
                .cheeringText(cheeringMessageRequestDto.getCheeringText())
                .user(user)
                .status(Status.A)
                .build();
    }
}
