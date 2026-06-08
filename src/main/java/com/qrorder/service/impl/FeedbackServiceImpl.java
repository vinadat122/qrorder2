package com.qrorder.service.impl;

import com.qrorder.dto.feedback.CreateFeedbackRequest;
import com.qrorder.dto.feedback.FeedbackResponse;

import com.qrorder.entity.Feedback;
import com.qrorder.entity.TableSession;
import com.qrorder.entity.enums.SessionStatus;

import com.qrorder.repository.FeedbackRepository;
import com.qrorder.repository.TableSessionRepository;

import com.qrorder.service.FeedbackService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl
        implements FeedbackService {

    private final FeedbackRepository
            feedbackRepository;

    private final TableSessionRepository
            sessionRepository;

    @Override
    public void createFeedback(
            CreateFeedbackRequest request
    ) {

        TableSession session =

                sessionRepository
                        .findById(
                                request.getSessionId()
                        )
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Session not found"
                                )
                        );

        if(session.getStatus()
                != SessionStatus.CLOSED) {

            throw new RuntimeException(
                    "Only completed session can be rated"
            );
        }

        if(feedbackRepository.existsBySessionId(
                session.getId()
        )) {

            throw new RuntimeException(
                    "Feedback already submitted"
            );
        }

        Feedback feedback =

                Feedback.builder()

                        .session(session)

                        .rating(
                                request.getRating()
                        )

                        .comment(
                                request.getComment()
                        )

                        .createdAt(
                                LocalDateTime.now()
                        )

                        .build();

        feedbackRepository.save(
                feedback
        );
    }

    @Override
    public List<FeedbackResponse>
    getFeedbacks() {

        return feedbackRepository

                .findAllByOrderByCreatedAtDesc()

                .stream()

                .map(feedback ->

                        FeedbackResponse.builder()

                                .id(
                                        feedback.getId()
                                )

                                .sessionId(
                                        feedback
                                                .getSession()
                                                .getId()
                                )

                                .rating(
                                        feedback.getRating()
                                )

                                .comment(
                                        feedback.getComment()
                                )

                                .createdAt(
                                        feedback.getCreatedAt()
                                )

                                .build()
                )

                .toList();
    }
}
