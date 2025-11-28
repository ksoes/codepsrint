package io.github.ksoes.quiz.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ksoes.quizdtl.dto.QAItem;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class QAParser {
    private final ObjectMapper mapper = new ObjectMapper();

    public List<QAItem> parse(String json) {
        try {
            json = clean(json);
            return mapper.readValue(json, new TypeReference<List<QAItem>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String clean(String s) {
        return s.replace("```json", "")
                .replace("```", "")
                .trim();
    }
}
