package com.pro.playground.sec02.repository;

import com.pro.playground.sec02.entity.CustomerOrder;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CustomerOrderRepository extends ReactiveCrudRepository<CustomerOrder, Integer> {
    @Query("""
            SELECT
              co.order_id,
                 c.name AS customer_name,
                 p.description AS product_name,
                 co.amount,
                 co.order_date
            FROM
                customer c
            INNER JOIN customer_order co ON c.id = co.customer_id
            INNER JOIN product p ON co.product_id = p.id
            WHERE
                c.name = :name
                """)
    Flux<CustomerOrder> findOrderByCustomer(String name);
}

