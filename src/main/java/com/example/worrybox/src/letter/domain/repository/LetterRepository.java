package com.example.worrybox.src.letter.domain.repository;

import com.example.worrybox.src.letter.api.dto.response.GetLettersRes;
import com.example.worrybox.src.letter.domain.Letter;
import com.example.worrybox.utils.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface LetterRepository  extends JpaRepository<Letter, Long> {
// MySQL 버전
    @Query("select l.id as letterId, l.user.id as userId," +
            "   l.letterText as letter, date_format(l.createdAt, '%Y-%m-%d') as sendDate, l.arrivalDate as arrivalDate" +
            "   from Letter as l" +
            "   where l.user.id=:userId and l.status = 'A' and l.arrivalDate=:date" +
            "   order by l.createdAt")
    List<GetLettersRes> findAllLetters(@Param("userId") Long userId, @Param("date") String date);

// H2 버전
//    @Query("select l.id as letterId, l.user.id as userId," +
//            "   l.letter_text as letter, FORMATDATETIME(l.createdAt, 'yyyy-MM-dd') as sendDate, l.arrival_date as arrivalDate" +
//            "   from Letter as l" +
//            "   where l.user.id=:userId and l.status = 'A' and l.arrival_date=:date" +
//            "   order by l.createdAt")
//    List<GetLettersRes> findAllLetters(@Param("userId") Long userId, @Param("date") String date);

    @Query("select l.id as letterI from Letter as l" +
            "   where l.user.id=:userId and l.status = 'A' and l.arrivalDate=:date" +
            "   order by l.createdAt")
    List<GetLetterId> findTodayLetters(@Param("userId") Long userId, @Param("date") String date);
}