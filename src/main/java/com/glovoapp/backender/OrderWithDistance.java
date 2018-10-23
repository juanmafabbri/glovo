package com.glovoapp.backender;

import java.util.Objects;

public class OrderWithDistance {
    private Order order;
    private double distance;


    public OrderWithDistance withOrder(Order order) {
        this.order = order;
        return this;
    }

    public OrderWithDistance withDistance(double distance) {
        this.distance = distance;
        return this;
    }

    public double getDistance() {
        return distance;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderWithDistance that = (OrderWithDistance) o;
        return Double.compare(that.distance, distance) == 0 &&
                Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, distance);
    }

    @Override
    public String toString() {
        return "OrderWithDistance{" +
                "order=" + order +
                ", distance=" + distance +
                '}';
    }
}
