package com.qrorder.dto.feedback;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FeedbackResponse {

    private Long id;

    private Long sessionId;

    private Integer rating;

    private String comment;

    private LocalDateTime createdAt;
}
