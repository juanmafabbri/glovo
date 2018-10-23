package com.glovoapp.backender;

import java.util.HashMap;
import java.util.Map;

public class PrioritiseFactory {

    private static final Map<String, Prioritise> factory = new HashMap<>();
    private static final String VIP = "vip";
    private static final String FOOD = "food";
    private static final String CLOSE = "close";
    private static final String DEFAULT = "default";


    static {
        factory.put(VIP, new VIPPrioritise());
        factory.put(FOOD, new FoodPrioritise());
        factory.put(CLOSE, new ClosePrioritise());
        factory.put(DEFAULT, new ClosePrioritise());
    }

    public static Prioritise getPrioritise(String prioritiseKey) {
        String prioritise = prioritiseKey != null ? prioritiseKey.toLowerCase() : null;
        if (factory.containsKey(prioritise)) {
            return factory.get(prioritise.toLowerCase());
        }
        return factory.get(DEFAULT);
    }


}
