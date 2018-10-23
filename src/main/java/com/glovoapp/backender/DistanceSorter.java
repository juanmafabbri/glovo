package com.glovoapp.backender;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DistanceSorter {

    public static List<OrderWithDistance> sort(List<OrderWithDistance> orders) {
        Collections.sort(orders, (o1, o2) -> new Double(o1.getDistance()).compareTo(o2.getDistance()));
        return orders;
    }
}
