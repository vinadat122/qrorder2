package com.qrorder.controller;

import com.qrorder.dto.feedback.CreateFeedbackRequest;
import com.qrorder.dto.feedback.FeedbackResponse;

import com.qrorder.service.FeedbackService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService
            feedbackService;

    @PostMapping
    public Map<String, String>
    createFeedback(

            @Valid
            @RequestBody
            CreateFeedbackRequest request
    ) {

        feedbackService
                .createFeedback(
                        request
                );

        return Map.of(
                "message",
                "Feedback success"
        );
    }

    @GetMapping
    public List<FeedbackResponse>
    getFeedbacks() {

        return feedbackService
                .getFeedbacks();
    }
}