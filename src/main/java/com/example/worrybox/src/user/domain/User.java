package com.example.worrybox.src.user.domain;

import com.example.worrybox.src.user.api.dto.request.PostJoinReq;
import com.example.worrybox.utils.entity.BaseEntity;
import com.example.worrybox.utils.entity.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor(access= AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private Status status;
    @NotNull
    private String name;
    @NotNull
    private int password;

    private String worry_start_time;
    private String worry_end_time;

    public static User of(PostJoinReq postJoinReq) {
        return User.builder()
                .name(postJoinReq.getName())
                .password(postJoinReq.getPassword())
                .status(Status.A)
                .build();
    }
}
