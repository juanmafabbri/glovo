package com.glovoapp.backender;

import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Component
public class PrioritiseChecker {

    private List<String> prioritises;
    private double slotDistance;
    private ConfigurationParameters configurationParameters;

    public PrioritiseChecker(ConfigurationParameters configurationParameters) {
        this.configurationParameters = configurationParameters;
        prioritises = this.configurationParameters.getPrioritises();
        slotDistance = this.configurationParameters.getSlotDistanceInKilometers();
    }

    public Set<Order> execute(List<OrderWithDistance> filteredOrdersWithDistance) {
        Set<Order> ordersInSlots = new LinkedHashSet<>();
        if (validateParameters(filteredOrdersWithDistance)) {
            return ordersInSlots;
        }

        DistanceSorter.sort(filteredOrdersWithDistance);
        double maxDistance = filteredOrdersWithDistance.get(filteredOrdersWithDistance.size() - 1).getDistance();
        int slots = obtainSlots(maxDistance);

        for (int currentSlot = 0; currentSlot <= slots; currentSlot++) {

            for (int prioritiseOrder = 0; prioritiseOrder < prioritises.size(); prioritiseOrder++) {

                PrioritiseFactory.getPrioritise(prioritises.get(prioritiseOrder))
                        .apply(filteredOrdersWithDistance, currentSlot, ordersInSlots, slotDistance);
            }

        }

        return ordersInSlots;
    }

    private boolean validateParameters(List<OrderWithDistance> filteredOrdersWithDistance) {
        return filteredOrdersWithDistance == null || filteredOrdersWithDistance.size() == 0;
    }

    private int obtainSlots(double maxDistance) {
        if (slotDistance != 0) {
            int slots = (int) (maxDistance / slotDistance);
            return (maxDistance % slotDistance != 0) ? slots + 1 : slots;
        }
        return 1;
    }
}
