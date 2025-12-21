package com.abhicom.userservice.repository;

import com.abhicom.userservice.model.User;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("""
                SELECT DISTINCT u
                FROM User u
                LEFT JOIN FETCH u.orders
            """)
    List<User> findAllWithOrders();

    @EntityGraph(attributePaths = "orders")
    @Query("SELECT u FROM User u")
    List<User> findAllWithOrdersGraph();
}
