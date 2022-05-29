package ru.itis.delivery_cube.service;

import org.springframework.stereotype.Service;
import ru.itis.delivery_cube.dto.RegistrationForm;

@Service
public interface AccountService {

    void registerAccount(RegistrationForm form);
}
