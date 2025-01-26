package com.codingshuttle.aopApp.service;

public interface ShipmentService {
    String orderPackage(Long orderId);
    String trackPackage(Long orderId);
}
