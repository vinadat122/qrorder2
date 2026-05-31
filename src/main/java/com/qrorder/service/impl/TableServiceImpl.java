package com.qrorder.service.impl;

import com.qrorder.dto.table.request.CreateTableRequest;
import com.qrorder.dto.table.request.
        ReserveTableRequest;
import com.qrorder.entity.RestaurantTable;
import com.qrorder.entity.enums.TableStatus;
import com.qrorder.repository.RestaurantTableRepository;
import com.qrorder.repository.TableSessionRepository;
import com.qrorder.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.qrorder.entity.TableSession;
import com.qrorder.entity.enums.SessionStatus;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final RestaurantTableRepository tableRepository;
    private final TableSessionRepository
            sessionRepository;

    @Override
    public void createTable(
            CreateTableRequest request
    ) {

        RestaurantTable table =
                RestaurantTable.builder()

                        .tableNumber(
                                request.getTableNumber()
                        )

                        .capacity(
                                request.getCapacity()
                        )

                        .qrToken(
                                UUID.randomUUID()
                                        .toString()
                        )

                        .status(
                                TableStatus.EMPTY
                        )

                        .build();

        tableRepository.save(table);
    }

    @Override
    public List<RestaurantTable> getTables() {

        return tableRepository.findAll();
    }

    @Override
    public void reserveTable(

            Long tableId,

            ReserveTableRequest request
    ) {

        RestaurantTable table =
                tableRepository.findById(tableId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Table not found"
                                ));

        if(table.getStatus()
                != TableStatus.EMPTY) {

            throw new RuntimeException(
                    "Table unavailable"
            );
        }

        table.setStatus(
                TableStatus.RESERVED
        );

        table.setReservedAt(
                LocalDateTime.now()
        );

        table.setReservationName(
                request.getName()
        );

        table.setReservationPhone(
                request.getPhone()
        );

        tableRepository.save(table);
    }

    @Override
    @Transactional
    public Long checkIn(Long tableId) {

        RestaurantTable table =
                tableRepository.findById(tableId)
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Table not found"
                                )
                        );

        if(table.getStatus()
                != TableStatus.RESERVED) {

            throw new RuntimeException(
                    "Table is not reserved"
            );
        }

        Optional<TableSession>
                existingSession =

                sessionRepository
                        .findByTableIdAndStatus(

                                tableId,

                                SessionStatus.OPEN
                        );

        if(existingSession.isPresent()) {

            return existingSession
                    .get()
                    .getId();
        }

        table.setStatus(
                TableStatus.OCCUPIED
        );

        tableRepository.save(table);

        TableSession session =
                new TableSession();

        session.setTable(table);

        session.setSessionToken(
                UUID.randomUUID()
                        .toString()
        );

        session.setStatus(
                SessionStatus.OPEN
        );

        session.setStartTime(
                LocalDateTime.now()
        );

        TableSession savedSession =
                sessionRepository.save(session);

        return savedSession.getId();
    }

    @Override
    public void resetTable(Long tableId) {

        RestaurantTable table =
                tableRepository.findById(tableId)
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Table not found"
                                )
                        );

        if(table.getStatus()
                != TableStatus.PAID) {

            throw new RuntimeException(
                    "Table is not paid yet"
            );
        }

        table.setStatus(
                TableStatus.EMPTY
        );

        table.setReservedAt(null);

        table.setReservationName(null);

        table.setReservationPhone(null);

        tableRepository.save(table);
    }
}