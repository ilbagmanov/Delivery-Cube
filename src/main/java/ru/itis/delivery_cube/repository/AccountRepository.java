package ru.itis.delivery_cube.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.delivery_cube.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}
