package com.glovoapp.backender;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrioritiseCheckerTest {

    private PrioritiseChecker prioritiseChecker;

    @Test
    @DisplayName("Execute when the prioritises are first vip and second food and thrid close")
    void executeWhenThePrioritisesAreFirstVipSecondFoodThirdClose() {
        //Given
        List<String> wordsRequireBox = new ArrayList<>();
        List<String> vehicles = new ArrayList<>();;
        int distanceToNeedVehicle = 0;

        int slotDistance = 1000;

        List<String> prioritises = new ArrayList<>();
        prioritises.add("vip");
        prioritises.add("food");
        prioritises.add("close");


        ConfigurationParameters configurationParameters = new ConfigurationParameters(wordsRequireBox, slotDistance,
                prioritises, vehicles, distanceToNeedVehicle);

        Order orderA = anOrder("aaaa", true, true);
        OrderWithDistance orderWithDistanceA = anOrderWithDistance(orderA, 3);

        Order orderB = anOrder("bbbb", true, false);
        OrderWithDistance orderWithDistanceB = anOrderWithDistance(orderB, 1);

        Order orderC = anOrder("cccc", true, false);
        OrderWithDistance orderWithDistanceC = anOrderWithDistance(orderC, 2);

        Order orderD = anOrder("dddd", false, true);
        OrderWithDistance orderWithDistanceD = anOrderWithDistance(orderD, 1.2);

        List<OrderWithDistance> orderWithDistanceList = generateOrderWithDistances(orderWithDistanceA, orderWithDistanceB,
                orderWithDistanceC, orderWithDistanceD);

        prioritiseChecker = new PrioritiseChecker(configurationParameters);
        //When
        Set<Order> prioritizedOrders = prioritiseChecker.execute(orderWithDistanceList);

        //Then
        assertEquals(expectedprioritizedOrders(orderB, orderD, orderC, orderA), prioritizedOrders);
    }


    @Test
    @DisplayName("Execute when the prioritises are first food and second vip and thrid close")
    void executeWhenThePrioritisesAreFirstFoodSecondVipThridClose() {
        //Given
        List<String> wordsRequireBox = new ArrayList<>();
        List<String> vehicles = new ArrayList<>();;
        int distanceToNeedVehicle = 0;

        int slotDistance = 1000;

        List<String> prioritises = new ArrayList<>();
        prioritises.add("food");
        prioritises.add("vip");
        prioritises.add("close");

        ConfigurationParameters configurationParameters = new ConfigurationParameters(wordsRequireBox, slotDistance, prioritises,
                vehicles, distanceToNeedVehicle);

        Order orderA = anOrder("aaaa", true, true);
        OrderWithDistance orderWithDistanceA = anOrderWithDistance(orderA, 3);

        Order orderB = anOrder("bbbb", false, false);
        OrderWithDistance orderWithDistanceB = anOrderWithDistance(orderB, 1);

        Order orderC = anOrder("cccc", true, false);
        OrderWithDistance orderWithDistanceC = anOrderWithDistance(orderC, 2);

        Order orderD = anOrder("dddd", false, true);
        OrderWithDistance orderWithDistanceD = anOrderWithDistance(orderD, 1.2);

        List<OrderWithDistance> orderWithDistanceList = generateOrderWithDistances(orderWithDistanceA, orderWithDistanceB, orderWithDistanceC, orderWithDistanceD);

        prioritiseChecker = new PrioritiseChecker(configurationParameters);

        //When
        Set<Order> prioritizedOrders = prioritiseChecker.execute(orderWithDistanceList);


        //Then
        assertEquals(expectedprioritizedOrders(orderD, orderB, orderC, orderA), prioritizedOrders);
    }

    @Test
    @DisplayName("Execute when the parameter orderWithDistanceList is a empety return a emptyList")
    void executeWhenTheParameterOrderWithDistanceListIsEmpetyReturnAEmptyList() {
        //Given
        ConfigurationParameters configurationParameters = Mockito.mock(ConfigurationParameters.class);
        prioritiseChecker = new PrioritiseChecker(configurationParameters);
        List<OrderWithDistance> orderWithDistanceList = new ArrayList<>();

        //when
        Set<Order> prioritizedOrders = prioritiseChecker.execute(orderWithDistanceList);

        //Then
        assertEquals(aEmptyOrderSet(), prioritizedOrders);
    }

    private Set<Order> aEmptyOrderSet() {
        Set<Order> ordersInSlots = new LinkedHashSet<>();
        return ordersInSlots;
    }

    private Set<Order> expectedprioritizedOrders(Order firstOrder, Order secondOrder, Order thirdOrder, Order fourthOrder ) {
        Set<Order> expectedOrders = new LinkedHashSet<>();
        expectedOrders.add(firstOrder);
        expectedOrders.add(secondOrder);
        expectedOrders.add(thirdOrder);
        expectedOrders.add(fourthOrder);
        return expectedOrders;
    }


    private OrderWithDistance anOrderWithDistance(Order order, double distance) {
        return new OrderWithDistance()
                .withOrder(order)
                .withDistance(distance);
    }

    private Order anOrder(String id, boolean vip, boolean food) {
        return new Order()
                .withId(id)
                .withFood(food)
                .withVip(vip);
    }

    private List<OrderWithDistance> generateOrderWithDistances(OrderWithDistance orderWithDistanceA, OrderWithDistance orderWithDistanceB,
                                                               OrderWithDistance orderWithDistanceC, OrderWithDistance orderWithDistanceD) {
        List<OrderWithDistance> ordersWithDistance = new ArrayList<>();
        ordersWithDistance.add(orderWithDistanceA);
        ordersWithDistance.add(orderWithDistanceB);
        ordersWithDistance.add(orderWithDistanceC);
        ordersWithDistance.add(orderWithDistanceD);
        return ordersWithDistance;
    }
}