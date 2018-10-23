package com.glovoapp.backender;

import java.util.List;
import java.util.Set;

public interface Prioritise {

    void apply(List<OrderWithDistance> filteredOrdersWithDistance, int currentSlot, Set<Order> slotOrders, double slotDistance);
}
