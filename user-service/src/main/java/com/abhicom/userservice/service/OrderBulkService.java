package com.abhicom.userservice.service;

import com.abhicom.userservice.dto.BulkCreateOrdersRequest;
import com.abhicom.userservice.model.*;
import com.abhicom.userservice.repository.OrderRepository;
import com.abhicom.userservice.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderBulkService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    // Tune this to your batch_size (often same or 2x)
    private static final int CHUNK_SIZE = 50;

    public OrderBulkService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public int bulkCreateOrders(BulkCreateOrdersRequest request) {
        int created = 0;

        List<Orders> buffer = new ArrayList<>(CHUNK_SIZE);

        for (BulkCreateOrdersRequest.OrderCreate o : request.getOrders()) {
            User userRef = entityManager.getReference(User.class, o.getUserId());

            Orders order = new Orders();
            order.setUser(userRef);
            order.setStatus(o.getStatus());
            order.setCreatedAt(Instant.now());

            BigDecimal total = BigDecimal.ZERO;

            for (BulkCreateOrdersRequest.ItemCreate it : o.getItems()) {
                OrderItemEntity item = new OrderItemEntity();
                item.setOrder(order);
                item.setSku(it.getSku());
                item.setQuantity(it.getQuantity());
                item.setPrice(it.getPrice());
                order.getItems().add(item);

                total = total.add(it.getPrice().multiply(BigDecimal.valueOf(it.getQuantity())));
            }

            order.setTotalAmount(total);

            buffer.add(order);

            if (buffer.size() == CHUNK_SIZE) {
                saveChunk(buffer);
                created += buffer.size();
                buffer.clear();
            }
        }

        if (!buffer.isEmpty()) {
            saveChunk(buffer);
            created += buffer.size();
        }

        return created;
    }

    private void saveChunk(List<Orders> chunk) {
        // saveAll is okay IF batching is enabled + you flush/clear per chunk
        orderRepository.saveAll(chunk);

        // Force DB write now (send batched SQL)
        orderRepository.flush();

        // Clear persistence context to avoid memory growth
        entityManager.clear();
    }
}
