package com.example.worrybox.src;

import com.example.worrybox.src.cloud.application.CloudWordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final CloudWordService cloudWordService;

    @GetMapping("/test")
    public ResponseEntity<String> orderList() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text", "plain", StandardCharsets.UTF_8));
        return new ResponseEntity<>("테스트 중입니다.", headers, HttpStatus.OK);
    }
    @GetMapping("/cloud")
    public ResponseEntity<List<String>> cloud(){
        return new ResponseEntity<>(cloudWordService.setKomoran(),HttpStatus.OK);
    }

}
