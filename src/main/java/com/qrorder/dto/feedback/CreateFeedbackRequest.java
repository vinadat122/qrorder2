package com.qrorder.dto.feedback;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class CreateFeedbackRequest {

    @NotNull
    private Long sessionId;

    @Min(1)
    @Max(5)
    private Integer rating;

    private String comment;
}
