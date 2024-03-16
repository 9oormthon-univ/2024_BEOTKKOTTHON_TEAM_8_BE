package com.example.worrybox.src.letter.domain;

import com.example.worrybox.src.user.domain.User;
import com.example.worrybox.utils.entity.BaseEntity;
import com.example.worrybox.utils.entity.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor(access= AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Letter extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @NotBlank
    private String letter_text;
    private Date arrival_date;

    public static Letter of(User user, String letter, Date arrivalDate) {
        return Letter.builder()
                .user(user)
                .letter_text(letter)
                .arrival_date(arrivalDate)
                .status(Status.A)
                .build();
    }
}
