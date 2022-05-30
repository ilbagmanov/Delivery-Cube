package ru.itis.delivery_cube.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.delivery_cube.model.Account;
import ru.itis.delivery_cube.model.AccountsRequests;
import ru.itis.delivery_cube.repository.AccountsRequestsRepository;

import java.util.List;

@Service
public class AccountsRequestsServiceImpl implements AccountsRequestsService {

    @Autowired
    private AccountsRequestsRepository accountsRequestsRepository;

    @Override
    public void save(AccountsRequests accountsRequests) {
        accountsRequestsRepository.save(accountsRequests);
    }

    @Override
    public List<AccountsRequests> getAllByAccount() {
        return accountsRequestsRepository.getAll();
    }
}
