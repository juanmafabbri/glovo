package com.glovoapp.backender;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ClosePrioritise implements Prioritise {

    @Override
    public void apply(List<OrderWithDistance> filteredOrdersWithDistance, int currentSlot, Set<Order> slotOrders, double slotDistance) {

        slotOrders.addAll(filteredOrdersWithDistance
                .stream()
                .filter(orderWithDistance -> belongsToTheSlot(currentSlot, orderWithDistance, slotDistance)
                        && isOnlyByClose(orderWithDistance))
                .map(orderWithDistanceToMap -> orderWithDistanceToMap.getOrder())
                .collect(Collectors.toList()));
    }

    private boolean isOnlyByClose(OrderWithDistance orderWithDistance) {
        return !(orderWithDistance.getOrder().getFood() || orderWithDistance.getOrder().getVip());
    }

    private boolean belongsToTheSlot(int currentSlot, OrderWithDistance orderWithDistance, double slotDistance) {
        return orderWithDistance.getDistance() >= (slotDistance * currentSlot)
                && orderWithDistance.getDistance() < (slotDistance * (currentSlot + 1));
    }

}
