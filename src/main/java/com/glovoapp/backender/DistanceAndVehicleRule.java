package com.glovoapp.backender;

import java.util.List;

import org.springframework.stereotype.Component;

import static com.glovoapp.backender.DistanceCalculator.calculateDistance;

@Component
public class DistanceAndVehicleRule extends Rule {

    private List<String> motorVehicles;
    private double distanceToNeedVehicle;

    public DistanceAndVehicleRule(ConfigurationParameters configurationParameters) {
        this.motorVehicles = configurationParameters.getVehicles();
        this.distanceToNeedVehicle = configurationParameters.getDistanceToNeedVehicle();
    }

    @Override
    public boolean apply(boolean previousRuleResult) {

        if (motorVehicles.contains(getCourier().getVehicle().toString())) {
            return true && previousRuleResult;
        }

        if (calculateDistance(getCourier().getLocation(), getOrder().getPickup()) < distanceToNeedVehicle) {
            return true && previousRuleResult;
        }

        return false;
    }

}
