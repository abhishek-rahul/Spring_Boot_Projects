package com.abhicom.userservice.repository;

import com.abhicom.userservice.model.Orders;
import com.abhicom.userservice.repository.projection.OrderLightProjection;
import com.abhicom.userservice.dto.OrderLightDto;
import com.abhicom.userservice.model.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.Instant;

public interface OrderRepository extends JpaRepository<Orders, Long> {

  Page<Orders> findByStatus(OrderStatus status, Pageable pageable);

  Page<Orders> findByUserEmailContainingIgnoreCase(String email, Pageable pageable);

  Page<Orders> findByStatusAndCreatedAtBetween(
      OrderStatus status, Instant from, Instant to, Pageable pageable);

  /**
   * ✅ JPQL: Fully NULL-safe for Postgres
   * - Email uses COALESCE
   * - Dates use COALESCE pattern to avoid "? is null OR created_at >= ?" which
   * can cause 42P18
   * - Amount filters use COALESCE similarly
   */
  @Query("""
          SELECT o
          FROM Orders o
          JOIN o.user u
          WHERE (:status IS NULL OR o.status = :status)
            AND LOWER(u.email) LIKE CONCAT('%', LOWER(COALESCE(:email, '')), '%')
            AND o.createdAt >= COALESCE(:from, o.createdAt)
            AND o.createdAt <= COALESCE(:to, o.createdAt)
            AND o.totalAmount >= COALESCE(:minAmount, o.totalAmount)
            AND o.totalAmount <= COALESCE(:maxAmount, o.totalAmount)
          ORDER BY o.createdAt DESC
      """)
  Page<Orders> searchOrdersJpql(
      @Param("status") OrderStatus status,
      @Param("email") String email,
      @Param("from") Instant from,
      @Param("to") Instant to,
      @Param("minAmount") BigDecimal minAmount,
      @Param("maxAmount") BigDecimal maxAmount,
      Pageable pageable);

  /**
   * ✅ Native query: also NULL-safe and countQuery matches filters
   */
  @Query(value = """
          SELECT o.*
          FROM orders o
          JOIN users u ON u.id = o.user_id
          WHERE (:status IS NULL OR o.status = CAST(:status AS VARCHAR))
            AND (COALESCE(:email, '') = '' OR u.email ILIKE CONCAT('%', :email, '%'))
            AND o.created_at >= COALESCE(:from, o.created_at)
            AND o.created_at <= COALESCE(:to, o.created_at)
            AND o.total_amount >= COALESCE(:minAmount, o.total_amount)
            AND o.total_amount <= COALESCE(:maxAmount, o.total_amount)
          ORDER BY o.created_at DESC
      """, countQuery = """
          SELECT COUNT(*)
          FROM orders o
          JOIN users u ON u.id = o.user_id
          WHERE (:status IS NULL OR o.status = CAST(:status AS VARCHAR))
            AND (COALESCE(:email, '') = '' OR u.email ILIKE CONCAT('%', :email, '%'))
            AND o.created_at >= COALESCE(:from, o.created_at)
            AND o.created_at <= COALESCE(:to, o.created_at)
            AND o.total_amount >= COALESCE(:minAmount, o.total_amount)
            AND o.total_amount <= COALESCE(:maxAmount, o.total_amount)
      """, nativeQuery = true)
  Page<Orders> searchOrdersNative(
      @Param("status") String status,
      @Param("email") String email,
      @Param("from") Instant from,
      @Param("to") Instant to,
      @Param("minAmount") BigDecimal minAmount,
      @Param("maxAmount") BigDecimal maxAmount,
      Pageable pageable);

  // ✅ Interface Projection (Spring Data maps selected columns to interface
  // getters)
  @Query("""
          SELECT
            o.id as orderId,
            o.status as status,
            o.totalAmount as totalAmount,
            o.createdAt as createdAt,
            u.email as userEmail
          FROM Orders o
          JOIN o.user u
          WHERE (:status IS NULL OR o.status = :status)
            AND LOWER(u.email) LIKE CONCAT('%', LOWER(COALESCE(:email, '')), '%')
      """)

  Page<OrderLightProjection> findLightOrdersInterface(
      @Param("status") OrderStatus status,
      @Param("email") String email,
      Pageable pageable);

  // ✅ DTO Projection (JPQL constructor expression)
  @Query("""
          SELECT new com.abhicom.userservice.dto.OrderLightDto(
            o.id,
            o.status,
            o.totalAmount,
            o.createdAt,
            u.email
          )
          FROM Orders o
          JOIN o.user u
          WHERE (:status IS NULL OR o.status = :status)
            AND LOWER(u.email) LIKE CONCAT('%', LOWER(COALESCE(:email, '')), '%')
      """)

  Page<OrderLightDto> findLightOrdersDto(
      @Param("status") OrderStatus status,
      @Param("email") String email,
      Pageable pageable);
}
