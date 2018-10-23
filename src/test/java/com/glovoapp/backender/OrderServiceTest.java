package com.glovoapp.backender;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderServiceTest {

    private OrderService orderService;

    @Test
    void findByCourierId() {
        //Given
        List<String> prioritises = new ArrayList<>();
        prioritises.add("food");
        prioritises.add("vip");
        prioritises.add("close");


        int slotDistance = 1600;

        List<String> wordsRequireBox = new ArrayList<>();
        wordsRequireBox.add("pizza");
        wordsRequireBox.add("cake");
        wordsRequireBox.add("flamingo");

        List<String> vehicles = new ArrayList<>();
        vehicles.add("MOTORCYCLE");
        vehicles.add("ELECTRIC_SCOOTER");

        double distanceToNeedVehicle = 3;

        ConfigurationParameters configurationParameters = new ConfigurationParameters(wordsRequireBox, slotDistance, prioritises,
                vehicles, distanceToNeedVehicle);

        CourierRepository courierRepository = new CourierRepository();
        OrderRepository orderRepository = new OrderRepository();

        orderService = new OrderService(orderRepository, courierRepository, configurationParameters);
        Set<Order> resultantOrders = orderService.findByCourierId("courier-2");

        assertEquals(expectedSetOrder(), resultantOrders);
    }

    @Test
    void findByCourierIdWithNonExistentId() {
        //Given
        ConfigurationParameters configurationParameters = Mockito.mock(ConfigurationParameters.class);

        CourierRepository courierRepository = new CourierRepository();
        OrderRepository orderRepository = new OrderRepository();

        orderService = new OrderService(orderRepository, courierRepository, configurationParameters);
        Set<Order> resultantOrders = orderService.findByCourierId("non-existent");

        assertEquals(aEmptySet(), resultantOrders);

    }

    private Set<Order> expectedSetOrder() {
        Order order3 = new Order().withId("order-3").withDescription("flowers").withVip(true).withFood(false)
                .withPickup(new Location(41.3870194,2.1678584)).withDelivery(new Location(41.507877, 2.6759753));
        Order order1 = new Order().withId("order-1").withDescription("I want a pizza cut into very small slices").withVip(false).withFood(true)
                .withPickup(new Location(41.3965463, 2.1963997)).withDelivery(new Location(41.407834, 2.1675979));

        Set<Order> expected = new LinkedHashSet<>();
        expected.add(order3);
        expected.add(order1);

        return expected;
    }

    private Set<Order> aEmptySet() {
        return new LinkedHashSet<>();
    }
}