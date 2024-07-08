package com.example.newsservice.repository;

import com.example.newsservice.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Page<User> findAllFilterBy(Pageable pageable);

    Boolean existsByUsername(String username);

    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByUsername(String username);
}
