package ru.itis.delivery_cube.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.delivery_cube.model.Request;
import ru.itis.delivery_cube.repository.RequestRepository;

import javax.transaction.Transactional;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Override
    public Request getLastRequestByAccountId(Long accountId) {
        return requestRepository.getLastRequestByAccountId(accountId);
    }

    @Override
    public Request getRequestById(Long id) {
        return requestRepository.getRequestById(id);
    }

    @Override
    public Long save(Request request) {
        return requestRepository.save(request).getId();
    }
}
