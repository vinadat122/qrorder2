package com.qrorder.repository;

import com.qrorder.entity.Feedback;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository
        extends JpaRepository<Feedback, Long> {

    List<Feedback>
    findAllByOrderByCreatedAtDesc();

    boolean existsBySessionId(
            Long sessionId
    );

    long count();
}
