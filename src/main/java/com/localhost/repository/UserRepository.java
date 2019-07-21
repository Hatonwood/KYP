package com.localhost.repository;

import com.localhost.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaSpecificationExecutor<User>, JpaRepository<User, Integer> {

    public Optional<User> findByUsername(String username);

}
