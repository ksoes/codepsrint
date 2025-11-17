package io.github.ksoes.quizdtl.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatGptRequest {
    private String model = "gpt-5.1";
    private List<Message> messages;

    @Getter
    @Setter
    public static class Message {
        private String role;
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }

    public ChatGptRequest(String prompt) {
        this.messages = List.of(new Message("user", prompt));
    }
}
