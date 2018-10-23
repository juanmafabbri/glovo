package com.glovoapp.backender;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoxRuleTest {

    private BoxRule boxRule;

    @Test
    @DisplayName("Apply BoxRule when a courier without box and description is flowers")
    void apply() {
        //Given
        ConfigurationParameters configurationParameters = createConfigurationParameters();

        Courier courier = new Courier().withBox(false);
        Order order = new Order().withDescription("flowers");

        boxRule = new BoxRule(configurationParameters);
        boxRule.setOrder(order);
        boxRule.setCourier(courier);

        assertTrue(boxRule.apply(true));

    }

    @Test
    @DisplayName("Apply BoxRule when a courier without box and description is pizza")
    void applyCase2() {
        //Given
        ConfigurationParameters configurationParameters = createConfigurationParameters();

        Courier courier = new Courier().withBox(false);
        Order order = new Order().withDescription("pizza");

        boxRule = new BoxRule(configurationParameters);
        boxRule.setOrder(order);
        boxRule.setCourier(courier);

        assertFalse(boxRule.apply(true));
    }

    @Test
    @DisplayName("Apply BoxRule when a courier with box and description is flowers")
    void applyCase3() {
        //Given
        ConfigurationParameters configurationParameters = createConfigurationParameters();

        Courier courier = new Courier().withBox(true);
        Order order = new Order()
                .withDescription("flowers");

        boxRule = new BoxRule(configurationParameters);
        boxRule.setOrder(order);
        boxRule.setCourier(courier);

        assertTrue(boxRule.apply(true));
    }

    @Test
    @DisplayName("Apply BoxRule when a courier without box and description is flower and previusRuleResult is false" +
            "return the apply false")
    void applyCase4() {
        //Given
        ConfigurationParameters configurationParameters = createConfigurationParameters();

        Courier courier = new Courier()
                .withBox(false);
        Order order = new Order()
                .withDescription("flower");

        boxRule = new BoxRule(configurationParameters);
        boxRule.setOrder(order);
        boxRule.setCourier(courier);

        assertFalse(boxRule.apply(false));
    }

    private ConfigurationParameters createConfigurationParameters() {
        List<String> prioritises = new ArrayList<>();
        prioritises.add("food");
        prioritises.add("vip");
        prioritises.add("close");

        int slotDistance = 1600;

        List<String> wordsRequireBox = new ArrayList<>();
        wordsRequireBox.add("pizza");
        wordsRequireBox.add("cake");
        wordsRequireBox.add("flamingo");

        List<String> vehicles = new ArrayList<>();
        vehicles.add("MOTORCYCLE");
        vehicles.add("ELECTRIC_SCOOTER");

        double distanceToNeedVehicle = 3;

        return new ConfigurationParameters(wordsRequireBox, slotDistance, prioritises,
                vehicles, distanceToNeedVehicle);
    }
}