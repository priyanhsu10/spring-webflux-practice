package com.pro.playground.test.sec02;


import com.pro.playground.sec02.entity.CustomerOrder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.test.StepVerifier;

public class Lec04DatabaseClient extends AbstractTest {
    private final static Logger log = LoggerFactory.getLogger(Lec04DatabaseClient.class);
    @Autowired
    private DatabaseClient client;

    @Test
    public void testDatabaseClient() {
        var query = """
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
                    """;
        this.client.sql(query)
                .bind("name","mike")
                .mapProperties(CustomerOrder.class)
                .all()
                .doOnNext(x->log.info("{}",x))
                .as(StepVerifier::create)
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }
}
