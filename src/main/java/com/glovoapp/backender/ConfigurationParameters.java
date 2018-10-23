package com.glovoapp.backender;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationParameters {
    private final List<String> wordsRequireBox;
    private final List<String> prioritises;
    private final List<String> vehicles;
    private final double distanceToNeedVehicle;
    private final int slotDistance;

    @Autowired
    public ConfigurationParameters(@Value("#{'${backender.orders.require.box}'.split(',')}") List<String> wordsRequireBox,
                                   @Value("#{'${backender.orders.slots.meters}'}") int slotDistance,
                                   @Value("#{'${backender.orders.prioritise}'.split(',')}") List<String> prioritises,
                                   @Value("#{'${backender.orders.motor.vehicles}'.split(',')}") List<String> vehicles,
                                   @Value("#{'${backender.orders.min.distance.motor.vehicle}'}") double distanceToNeedVehicle
    ) {
        this.wordsRequireBox = wordsRequireBox;
        this.slotDistance = slotDistance;
        this.prioritises = prioritises;
        this.vehicles = vehicles;
        this.distanceToNeedVehicle = distanceToNeedVehicle;
    }


    public List<String> getWordsRequireBox() {
        return wordsRequireBox != null ? wordsRequireBox : new ArrayList<>();
    }

    public double getSlotDistanceInKilometers() {
        return slotDistance / 1000.0;
    }

    public List<String> getPrioritises() {
        return prioritises != null ? prioritises : new ArrayList<>();
    }

    public List<String> getVehicles() {
        return vehicles != null ? vehicles : new ArrayList<>();
    }

    public double getDistanceToNeedVehicle() {
        return distanceToNeedVehicle;
    }
}
