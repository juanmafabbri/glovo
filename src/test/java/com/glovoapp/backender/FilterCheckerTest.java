package com.glovoapp.backender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class FilterCheckerTest {


    private FilterChecker filterChecker;

    @Test
    @DisplayName("Courier located in Placa Catalunya, with Bicycle and without box")
    void executeFilterCheckerCase1() {
        //Given
        ConfigurationParameters configurationParameters = generateConfigurationParameters();

        filterChecker = new FilterChecker(configurationParameters);

        Location francescMacia = new Location(41.3925603, 2.1418532);
        Location placaCatalunya = new Location(41.3870194,2.1678584);
        Location parkGuel = new Location(41.4009975,2.1436434);
        Location barriGotic = new Location(41.3813667,2.1688177);

        Courier courier = createCourier(placaCatalunya, "courier-wo-box-1", false, Vehicle.BICYCLE);

        Order order1 = anOrder(francescMacia, "order-1", "Envelope");
        Order order2 = anOrder(parkGuel, "order-2", "2x Hot dog with Fries\\n1x Hot dog with Fries");
        Order order3 = anOrder(barriGotic, "order-3", "2x Pizzas");

        List<Order> originalOrders = new ArrayList<>();
        originalOrders.add(order1);
        originalOrders.add(order2);
        originalOrders.add(order3);

        //When
        List<OrderWithDistance> filteredOrders = filterChecker.execute(courier, originalOrders);
        //Then
        Assertions.assertEquals(expectedFilteredOrdersWithDistance(order1), filteredOrders);
    }


    @Test
    @DisplayName("Courier located in Placa Catalunya with Motorcycle and with box")
    void executeFilterCheckerCase2() {
        //Given
        ConfigurationParameters configurationParameters = generateConfigurationParameters();

        filterChecker = new FilterChecker(configurationParameters);

        Location francescMacia = new Location(41.3925603, 2.1418532);
        Location placaCatalunya = new Location(41.3870194,2.1678584);
        Location parkGuel = new Location(41.4009975,2.1436434);
        Location barriGotic = new Location(41.3813667,2.1688177);

        Courier courier = createCourier(placaCatalunya, "courier-with-box-2", true, Vehicle.MOTORCYCLE);

        Order order1 = anOrder(francescMacia, "order-1", "Envelope");
        Order order2 = anOrder(parkGuel, "order-2", "2x Hot dog with Fries\\n1x Hot dog with Fries");
        Order order3 = anOrder(barriGotic, "order-3", "2x Pizzas");

        List<Order> originalOrders = new ArrayList<>();
        originalOrders.add(order1);
        originalOrders.add(order2);
        originalOrders.add(order3);

        //When
        List<OrderWithDistance> filteredOrders = filterChecker.execute(courier, originalOrders);


        //Then
        Assertions.assertEquals(expectedFilteredOrdersWithDistance(order1, order2, order3), filteredOrders);
    }

    private ConfigurationParameters generateConfigurationParameters() {
        List<String> prioritises = new ArrayList<>();
        prioritises.add("food");
        prioritises.add("vip");
        prioritises.add("close");

        int slotDistance = 1000;

        List<String> wordsRequireBox = new ArrayList<>();
        wordsRequireBox.add("pizza");
        wordsRequireBox.add("cake");
        wordsRequireBox.add("flamingo");

        List<String> vehicles = new ArrayList<>();
        vehicles.add("MOTORCYCLE");
        vehicles.add("ELECTRIC_SCOOTER");

        double distanceToNeedVehicle = 2.5;

        return new ConfigurationParameters(wordsRequireBox, slotDistance, prioritises,
                vehicles, distanceToNeedVehicle);
    }

    @Test
    @DisplayName("when the parameter courier is null, execute return a emptyList")
    void executeFilterCheckerCaseEmptyCourier() {
        //Given
        ConfigurationParameters configurationParameters = mock(ConfigurationParameters.class);
        Order order = mock(Order.class);
        filterChecker = new FilterChecker(configurationParameters);
        List<Order> originalOrders = new ArrayList<>();
        originalOrders.add(order);

        //when
        List<OrderWithDistance> filteredOrders = filterChecker.execute(null, originalOrders);

        //Then
        assertEquals(aEmptyOrdersWithDistanteList(), filteredOrders);
    }

    @Test
    @DisplayName("when the parameter originalOrders is null, execute return a emptyList")
    void executeFilterCheckerCaseEmptyOrderList() {
        //Given
        ConfigurationParameters configurationParameters = mock(ConfigurationParameters.class);
        Courier courier = mock(Courier.class);
        filterChecker = new FilterChecker(configurationParameters);

        //when
        List<OrderWithDistance> filteredOrders = filterChecker.execute(courier, null);

        //Then
        assertEquals(aEmptyOrdersWithDistanteList(), filteredOrders);
    }

    private List<OrderWithDistance> aEmptyOrdersWithDistanteList() {
        List<OrderWithDistance> emptyOrders = new ArrayList<>();
        return emptyOrders;
    }


    private List<OrderWithDistance> expectedFilteredOrdersWithDistance(Order order1, Order order2, Order order3) {
        List<OrderWithDistance> filteredOrdersExpected = new ArrayList<>();
        OrderWithDistance expectedOrderWithDistance1 = new OrderWithDistance()
                .withOrder(order1)
                .withDistance(2.255190800485739);
        OrderWithDistance expectedOrderWithDistance2 = new OrderWithDistance()
                .withOrder(order2)
                .withDistance(2.5487099849765253);
        OrderWithDistance expectedOrderWithDistance3 = new OrderWithDistance()
                .withOrder(order3)
                .withDistance(0.6336263813261925);

        filteredOrdersExpected.add(expectedOrderWithDistance1);
        filteredOrdersExpected.add(expectedOrderWithDistance2);
        filteredOrdersExpected.add(expectedOrderWithDistance3);

        return filteredOrdersExpected;
    }


    private Courier createCourier(Location placaCatalunya, String id, boolean box, Vehicle vehicle) {
        return new Courier()
                .withId(id)
                .withLocation(placaCatalunya)
                .withBox(box)
                .withVehicle(vehicle);
    }

    private List<OrderWithDistance> expectedFilteredOrdersWithDistance(Order order) {
        List<OrderWithDistance> filteredOrdersExpected = new ArrayList<>();
        OrderWithDistance expectedOrderWithDistance = new OrderWithDistance()
                .withOrder(order)
                .withDistance(2.255190800485739);
        filteredOrdersExpected.add(expectedOrderWithDistance);
        return filteredOrdersExpected;
    }

    private Order anOrder(Location francescMacia, String id, String envelope) {
        return new Order()
                .withId(id)
                .withPickup(francescMacia)
                .withDescription(envelope);
    }
}