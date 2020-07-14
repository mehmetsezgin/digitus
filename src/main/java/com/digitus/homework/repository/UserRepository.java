package com.digitus.homework.repository;

import com.digitus.homework.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByUserName(String userName);

    @Query("SELECT COUNT(u) FROM User u WHERE u.loggedIn=?1")
    Long countByLoggedStatus(int loggedIn);

    @Query(value = "select count(*) from users where hour(timediff(NOW(), registration_complete_time)) < 24", nativeQuery = true)
    Integer countNewUsersIn24Hours();

}