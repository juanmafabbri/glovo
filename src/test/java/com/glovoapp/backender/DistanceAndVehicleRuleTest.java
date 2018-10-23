package com.glovoapp.backender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DistanceAndVehicleRuleTest {

    private DistanceAndVehicleRule distanceAndVehicleRule;

    @Test
    @DisplayName("Courier with Motorcycle")
    void applyCase1() {
        //Given
        ConfigurationParameters configurationParameters = createConfigurationParameters();

        Courier courier = new Courier()
                .withVehicle(Vehicle.MOTORCYCLE);

        distanceAndVehicleRule = new DistanceAndVehicleRule(configurationParameters);
        distanceAndVehicleRule.setCourier(courier);

        //When - then
        Assertions.assertTrue(distanceAndVehicleRule.apply(true));

    }

    @Test
    @DisplayName("Courier with Bicycle and close by less than 3 kilometers of pickup order")
    void applyCase2() {
        //Given
        ConfigurationParameters configurationParameters = createConfigurationParameters();

        Location placaCatalunya = new Location(41.3870194,2.1678584);
        Location barriGotic = new Location(41.3813667,2.1688177);

        Courier courier = new Courier()
                .withVehicle(Vehicle.BICYCLE)
                .withLocation(placaCatalunya);

        Order order = new Order().withPickup(barriGotic);

        distanceAndVehicleRule = new DistanceAndVehicleRule(configurationParameters);
        distanceAndVehicleRule.setCourier(courier);
        distanceAndVehicleRule.setOrder(order);

        //When - then
        Assertions.assertTrue(distanceAndVehicleRule.apply(true));

    }

    private ConfigurationParameters createConfigurationParameters() {
        int slotDistance = 1600;

        List<String> wordsRequireBox = new ArrayList<>();
        wordsRequireBox.add("pizza");
        wordsRequireBox.add("cake");
        wordsRequireBox.add("flamingo");

        List<String> vehicles = new ArrayList<>();
        vehicles.add("MOTORCYCLE");
        vehicles.add("ELECTRIC_SCOOTER");

        List<String> prioritises = new ArrayList<>();
        prioritises.add("food");
        prioritises.add("vip");
        prioritises.add("close");


        double distanceToNeedVehicle = 3;

        return new ConfigurationParameters(wordsRequireBox, slotDistance, prioritises,
                vehicles, distanceToNeedVehicle);
    }
}