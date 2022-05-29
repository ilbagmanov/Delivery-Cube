package ru.itis.delivery_cube.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.delivery_cube.model.Account;
import ru.itis.delivery_cube.model.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query(nativeQuery = true, value = "SELECT * from request where author_id_id = :accountId ORDER BY id DESC LIMIT 1")
    Request getLastRequestByAccountId(@Param("accountId") Long accountId);

    Request getRequestById(Long id);
}
