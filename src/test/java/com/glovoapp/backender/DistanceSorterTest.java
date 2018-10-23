package com.glovoapp.backender;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DistanceSorterTest {


    @Test
    @DisplayName("given three orders in a list return this list sorted")
    public void given_three_orders_in_a_list_return_this_list_sorted() {
        OrderWithDistance orderC = new OrderWithDistance()
                .withDistance(500);
        OrderWithDistance orderA = new OrderWithDistance()
                .withDistance(25);
        OrderWithDistance orderB = new OrderWithDistance()
                .withDistance(100);

        List<OrderWithDistance> orderWithDistances = new ArrayList<>();
        orderWithDistances.add(orderB);
        orderWithDistances.add(orderA);
        orderWithDistances.add(orderC);

        assertEquals(expectedOrders(orderA, orderB, orderC), DistanceSorter.sort(orderWithDistances));
    }

    private List<OrderWithDistance> expectedOrders(OrderWithDistance order1, OrderWithDistance order2, OrderWithDistance order3) {
        List<OrderWithDistance> orderWithDistances = new ArrayList<>();
        orderWithDistances.add(order1);
        orderWithDistances.add(order2);
        orderWithDistances.add(order3);
        return orderWithDistances;
    }
}
