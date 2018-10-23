package com.glovoapp.backender;

public abstract class Rule {
    private Order order;
    private Courier courier;

    public Rule withOrder(Order order) {
        this.order = order;
        return this;
    }

    public Rule withCourier(Courier courier) {
        this.courier = courier;
        return this;
    }

    public abstract boolean apply(boolean previusRuleResult);

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }
}
