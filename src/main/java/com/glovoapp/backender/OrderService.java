package com.glovoapp.backender;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CourierRepository courierRepository;
    private final ConfigurationParameters configurationParameters;


    @Autowired
    OrderService(OrderRepository orderRepository, CourierRepository courierRepository, ConfigurationParameters configurationParameters) {
        this.orderRepository = orderRepository;
        this.courierRepository = courierRepository;
        this.configurationParameters = configurationParameters;
    }

    Set<Order> findByCourierId(String courierId) {
        Courier courier = courierRepository.findById(courierId);
        List<Order> originalOrders = orderRepository.findAll();


        FilterChecker filterChecker = new FilterChecker(configurationParameters);
        //filter orders per courier with/out box, vehicle type and distance to pickup point
        List<OrderWithDistance> filteredOrdersWithDistance = filterChecker.execute(courier, originalOrders);

        //Iterations and charges for the number of slots
        PrioritiseChecker prioritiseChecker = new PrioritiseChecker(configurationParameters);

        return prioritiseChecker.execute(filteredOrdersWithDistance);
    }


}
