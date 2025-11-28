package io.github.ksoes.room.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ksoes.category.domain.Category;
import io.github.ksoes.category.repository.CategoryRepository;
import io.github.ksoes.quiz.domain.Quiz;
import io.github.ksoes.quiz.repository.QuizRepository;
import io.github.ksoes.quiz.service.GeminiService;
import io.github.ksoes.quiz.service.QAParser;
import io.github.ksoes.quizdtl.domain.QuizDtl;
import io.github.ksoes.quizdtl.dto.QAItem;
import io.github.ksoes.quizdtl.dto.QAResponse;
import io.github.ksoes.quizdtl.repository.QuizDtlRepository;
import io.github.ksoes.room.domain.Room;
import io.github.ksoes.room.dto.RoomCond;
import io.github.ksoes.room.dto.RoomDto;
import io.github.ksoes.room.repository.RoomRepository;
import io.github.ksoes.common.domain.RoomStatus;
import io.github.ksoes.common.domain.YesOrNo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {

    @Value("${open.api.chatgpt.key}")
    private String apiKey;

    @Value("${gemini.api.key}")
    private String geminiKey;

    private final RoomRepository roomRepository;
    private final CategoryRepository categoryRepository;
    private final QuizRepository quizRepository;
    private final QuizDtlRepository quizDtlRepository;
    private final QAParser qaParser;
    private final GeminiService geminiService;

    public List<RoomDto> searchRooms(RoomCond cond) {
        List<Room> rooms = roomRepository.findAllByCategoryIdAndStatusCd(cond.getCategoryId(), cond.getStatusCd());
        return rooms.stream()
                .map(RoomDto::new)
                .toList();
    }

    @Transactional
    public void createRoom() {
        List<Category> categories = categoryRepository.findAllByUseYn(YesOrNo.Y);
        for (Category category : categories) {
            // 카테고리별 준비상태의 방이 있는지 확인
            Boolean roomExist = roomRepository.existsByCategoryIdAndStatusCd(category.getId(), RoomStatus.WAITING.getStatus());

            // 준비상태의 방이 없을 경우
            if (!roomExist) {
                // 해당 카테고리의 방 생성
                roomRepository.save(Room.builder()
                        .category(category)
                        .statusCd(RoomStatus.WAITING.getStatus())
                        .build());

                // 문제 생성
                // 1.Gemini 호출(JSON 문자열 반환)
                String json = geminiService.generateQAJson(category.getCategoryName());

                // 2.JSON > JAVA 변환
                List<QAItem> qaList = qaParser.parse(json);

                // 3.Quiz 저장
                Quiz quiz = quizRepository.save(Quiz.builder()
                        .category(category)
                        .build());

                // 4.QuizDtl 저장
                for (QAItem qaItem : qaList) {
                    quizDtlRepository.save(QuizDtl.builder()
                            .quiz(quiz)
                            .question(qaItem.getQuestion())
                            .answer(qaItem.getAnswer())
                            .build());
                }
            }
        }
    }




    @Transactional
    public void test() {
        List<Category> categories = categoryRepository.findAllByUseYn(YesOrNo.Y);
        for (Category category : categories) {
            String prompt = """
                        당신은 학습용 Q&A 생성기입니다.
                        키워드 "%s"를 주제로 2개의 단답형으로 정답을 맞출 수 있는 Q&A를 JSON으로 출력하세요. 정답이 여러가지 일 경우 |를 사용해서 붙이세요.
                        [
                          { "question": "", "answer": "" }
                        ]
                    """.formatted(category.getCategoryName());

            Client client = Client.builder()
                    .apiKey(geminiKey)
                    .build();

            GenerateContentResponse response =
                    client.models.generateContent(
                            "gemini-2.5-flash",
                            prompt,
                            null);
            try {
                ObjectMapper mapper = new ObjectMapper();
                String json = response.text();
                json = json.replace("```json", "")
                        .replace("```", "")
                        .trim();
                List<QAItem> list = mapper.readValue(json, new TypeReference<List<QAItem>>() {
                });
                QAResponse qaResponseList = new QAResponse(list);
                log.info(qaResponseList.toString());

                Quiz quiz = quizRepository.save(Quiz.builder()
                        .category(category)
                        .build());
                for (QAItem qaItem : qaResponseList.getItems()) {
                    quizDtlRepository.save(QuizDtl.builder()
                            .quiz(quiz)
                            .question(qaItem.getQuestion())
                            .answer(qaItem.getAnswer())
                            .build());
                }

            } catch (IOException e) {
                throw new RuntimeException("JSON 파싱 실패: " + e.getMessage(), e);
            }
        }
    }
}
