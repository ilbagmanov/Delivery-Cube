package ru.itis.delivery_cube.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.delivery_cube.dto.RegistrationForm;
import ru.itis.delivery_cube.model.Account;
import ru.itis.delivery_cube.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void registerAccount(RegistrationForm form) {
        accountRepository.save(new Account(form.getUserId(), form.getUsername()));
    }
}
