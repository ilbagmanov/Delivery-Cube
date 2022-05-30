package ru.itis.delivery_cube.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.delivery_cube.model.AccountsRequests;

import java.util.List;

@Repository
public interface AccountsRequestsRepository extends JpaRepository<AccountsRequests, Long> {

    @Query(nativeQuery = true, value = "SELECT * from accounts_requests where account_id = :accountId")
    List<AccountsRequests> getAllByAccount_Id(@Param("accountId") Long accountId);
}
