package io.github.ksoes.room.service;

import io.github.ksoes.category.domain.Category;
import io.github.ksoes.category.repository.CategoryRepository;
import io.github.ksoes.quizdtl.dto.ChatGptRequest;
import io.github.ksoes.quizdtl.dto.ChatGptResponse;
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

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final CategoryRepository categoryRepository;

    @Value("${open.api.chatgpt.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.openai.com/v1/chat/completions")
            .defaultHeader("Content-Type", "application/json")
            .build();

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
            Room room = roomRepository.existsByCategoryId(category.getId());
            if (room == null) {
                roomRepository.save(Room.builder()
                        .category(category)
                        .statusCd(RoomStatus.WAITING.getStatus())
                        .build());
            }

            String prompt = """
                당신은 학습용 Q&A 생성기입니다.
                키워드 "%s"를 주제로 25개의 질문(question)과 답변(answer)을 만들어 주세요.
                
                아래 JSON 형식으로만 출력하세요:
                [
                  {
                    "question": "",
                    "answer": ""
                  }
                ]
                """.formatted(category.getCategoryName());

            ChatGptRequest request = new ChatGptRequest(prompt);
            ChatGptResponse response = webClient.post()
                    .header("Authorization", "Bearer " + apiKey)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(ChatGptResponse.class)
                    .block();

        }
    }

    @Transactional
    public void test() {
        List<Category> categories = categoryRepository.findAllByUseYn(YesOrNo.Y);
        for (Category category : categories) {
            String prompt = """
                당신은 학습용 Q&A 생성기입니다.
                키워드 "%s"를 주제로 25개의 질문(question)과 답변(answer)을 만들어 주세요.
                
                아래 JSON 형식으로만 출력하세요:
                [
                  {
                    "question": "",
                    "answer": ""
                  }
                ]
                """.formatted(category.getCategoryName());

            ChatGptRequest request = new ChatGptRequest(prompt);
            ChatGptResponse response = webClient.post()
                    .header("Authorization", "Bearer " + apiKey)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(ChatGptResponse.class)
                    .block();

            log.info(response.toString());
        }
    }
}
