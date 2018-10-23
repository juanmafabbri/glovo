package com.glovoapp.backender;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class VIPPrioritise implements Prioritise {

    @Override
    public void apply(List<OrderWithDistance> filteredOrdersWithDistance, int currentSlot, Set<Order> slotOrders, double slotDistance) {

        slotOrders.addAll(filteredOrdersWithDistance
                .stream()
                .filter(orderWithDistance -> belongsToTheSlot(currentSlot, orderWithDistance, slotDistance)
                        && isVip(orderWithDistance))
                .map(orderWithDistanceToMap -> orderWithDistanceToMap.getOrder())
                .collect(Collectors.toList()));
    }

    private Boolean isVip(OrderWithDistance orderWithDistance) {
        return orderWithDistance.getOrder().getVip();
    }

    private boolean belongsToTheSlot(int currentSlot, OrderWithDistance orderWithDistance, double slotDistance) {
        return orderWithDistance.getDistance() >= (slotDistance * currentSlot)
                && orderWithDistance.getDistance() < (slotDistance * (currentSlot + 1));
    }
}
