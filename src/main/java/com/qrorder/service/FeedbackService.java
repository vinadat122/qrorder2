package com.qrorder.service;

import com.qrorder.dto.feedback.CreateFeedbackRequest;
import com.qrorder.dto.feedback.FeedbackResponse;

import java.util.List;

public interface FeedbackService {

    void createFeedback(
            CreateFeedbackRequest request
    );

    List<FeedbackResponse>
    getFeedbacks();
}