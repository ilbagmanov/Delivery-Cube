package ru.itis.delivery_cube.service;

import ru.itis.delivery_cube.model.Account;
import ru.itis.delivery_cube.model.AccountsRequests;

import java.util.List;

public interface AccountsRequestsService {

    void save(AccountsRequests accountsRequests);

    List<AccountsRequests> getAllByAccount(Account account);
}
