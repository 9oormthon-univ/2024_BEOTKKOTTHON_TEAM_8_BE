package com.example.worrybox.src.memo.domain;

import com.example.worrybox.src.memo.api.dto.request.MemoRequestDto;
import com.example.worrybox.src.user.domain.User;
import com.example.worrybox.utils.entity.BaseEntity;
import com.example.worrybox.utils.entity.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor(access= AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Memo extends BaseEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "memo_id")
//    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    private String worryText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 정적 팩토링 메서드
    public static Memo of(User user,MemoRequestDto memoRequestDto){
        return Memo.builder()
                .worryText(memoRequestDto.getWorryText())
                .status(Status.A)
                .user(user)
                .build();
    }
}
