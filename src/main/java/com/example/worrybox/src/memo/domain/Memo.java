package com.example.worrybox.src.memo.domain;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memo_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    private String name;

    @NotNull
    private int password;

    private String worry_start_time;

    private String worry_end_time;

    private String worry_text;
}