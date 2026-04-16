package com.mada.server.util;

import java.util.List;
import java.util.Random;

public class NicknameGenerator {
    private static final List<String> ADJECTIVES = List.of(
        "행복한", "신나는", "용감한", "귀여운", "빠른",
        "조용한", "활발한", "따뜻한", "멋진", "씩씩한"
    );

    private static final List<String> NOUNS = List.of(
        "고양이", "강아지", "토끼", "판다", "여우",
        "햄스터", "펭귄", "코알라", "다람쥐", "너구리"
    );

    private static final Random RANDOM = new Random();

    public static String generate() {
        String adjective = ADJECTIVES.get(RANDOM.nextInt(ADJECTIVES.size()));
        String noun = NOUNS.get(RANDOM.nextInt(NOUNS.size()));
        int number = RANDOM.nextInt(900) + 100;
        return adjective + noun + number;
    }
}
