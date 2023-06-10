package com.ssafy.util;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.yaml.snakeyaml.util.UriEncoder;

@Api(tags = "Util", value = "카카오 블로그 검색 서비스")
@Slf4j
@RestController
@RequestMapping("/api/search")
public class KakaoSearchProxy {
    private static final String KakaoAK_KEY = "1124ee806e4487f632b0cff20ac28f7d";

    private WebClient client;

    public KakaoSearchProxy() {
        client = WebClient.builder().baseUrl("https://dapi.kakao.com/v2/search/blog")
                .defaultHeader("Authorization", "KakaoAK " + KakaoAK_KEY)
                .build();
    }

    @GetMapping
    public ResponseEntity<String> blogSearch(@RequestParam String keyword) {
        ResponseEntity<String> res = client.get().uri("?query=" + UriEncoder.encode(keyword)).retrieve().toEntity(String.class).block();
//        log.debug("res : {}", res);
        return res;
//        return new ResponseEntity<>(client.get().uri("?query=" + keyword).retrieve().bodyToMono(String.class).block(), HttpStatus.OK);
    }
}