package ru.itis.delivery_cube.service;

import org.springframework.stereotype.Service;
import ru.itis.delivery_cube.dto.RegistrationForm;
import ru.itis.delivery_cube.model.Account;


public interface AccountService {

    void registerAccount(RegistrationForm form);

    Account getAccountById(Long id);

    void updateAccountById(Long id, Integer status);
}
