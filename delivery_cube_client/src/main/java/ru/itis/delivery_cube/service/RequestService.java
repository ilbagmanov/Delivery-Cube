package ru.itis.delivery_cube.service;

import org.springframework.data.repository.query.Param;
import ru.itis.delivery_cube.model.Request;

public interface RequestService {
    Request getLastRequestByAccountId(Long accountId);

    Request getRequestById(Long id);

    Long save(Request request);
}
