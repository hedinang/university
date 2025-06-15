package com.example.university.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreQuestionRequest {
    String questionId;
    String questionerId;
    @NotNull
    String title;
    String content;
    String recipientId;

}
