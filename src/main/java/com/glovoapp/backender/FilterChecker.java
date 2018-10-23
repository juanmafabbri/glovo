package com.glovoapp.backender;

import java.util.ArrayList;
import java.util.List;

public class FilterChecker {

    private final ConfigurationParameters configurationParameters;


    public FilterChecker(ConfigurationParameters configurationParameters) {
        this.configurationParameters = configurationParameters;
    }

    public List<OrderWithDistance> execute(Courier courier, List<Order> originalOrders) {

        List<OrderWithDistance> filteredOrders = new ArrayList<>();
        if (validateParameters(courier, originalOrders)) {
            return filteredOrders;
        }
        //aplica las reglas
        for (Order order : originalOrders) {
            List<Rule> rules = new ArrayList<>();
            rules.add(new BoxRule(configurationParameters)
                    .withCourier(courier)
                    .withOrder(order));
            rules.add(new DistanceAndVehicleRule(configurationParameters)
                    .withCourier(courier)
                    .withOrder(order));

            RulesProcessor rulesProcessor = new RulesProcessor().withRules(rules);
            if (rulesProcessor.apply()) {
                filteredOrders.add(new OrderWithDistance()
                        .withOrder(order)
                        .withDistance(DistanceCalculator.calculateDistance(courier.getLocation(), order.getPickup())));
            }
        }
        return filteredOrders;
    }

    private boolean validateParameters(Courier courier, List<Order> originalOrders) {
        return originalOrders == null || originalOrders.size() == 0 || courier == null;
    }
}
