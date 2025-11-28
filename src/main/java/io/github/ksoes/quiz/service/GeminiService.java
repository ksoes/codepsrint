package io.github.ksoes.quiz.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeminiService {

    @Value("${gemini.api.key}")
    private String geminiKey;

    public String generateQAJson(String keyword) {
        String prompt = """
            당신은 학습용 Q&A 생성기입니다.
            키워드 "%s"를 주제로 2개의 단답형으로 정답을 맞출 수 있는 Q&A를 JSON으로 출력하세요. 정답이 여러가지 일 경우 |를 사용해서 붙이세요.
            [
              { "question": "", "answer": "" }
            ]
        """.formatted(keyword);

        Client client = Client.builder()
                .apiKey(geminiKey)
                .build();

        GenerateContentResponse response = client.models.generateContent(
                "gemini-2.5-flash",
                prompt,
                null
        );

        return response.text();
    }
}
