package com.qrorder.service;

import com.qrorder.entity.TableSession;

public interface TableSessionService {

    TableSession openSession(Long tableId);
    TableSession getActiveSession(
            Long tableId
    );
}