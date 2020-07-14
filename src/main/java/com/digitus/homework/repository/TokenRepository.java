package com.digitus.homework.repository;


import com.digitus.homework.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    @Query(value = "select count(*) from token where hour(timediff(NOW(), creation_time)) > 24", nativeQuery = true)
    Integer countExpiredConfirmations();
}
