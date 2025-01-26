package com.codingshuttle.aopApp.service.impl;

import com.codingshuttle.aopApp.aspects.annotations.MyLogging;
import com.codingshuttle.aopApp.service.ShipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ShipmentServiceImpl implements ShipmentService {

    @MyLogging
    @Override
    public String orderPackage(Long orderId) {
//        log.info("orderPackage method called in class ShipmentServiceImpl");

        try {
            log.info("Processing Order ID : {}",orderId);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("error occurred while processing Order ID {}",orderId);
        }
        return "Processed Order ID : "+orderId;
    }

    @Override
    @Transactional
    public String trackPackage(Long orderId) {
//        log.info("trackPackage method called in class ShipmentServiceImpl");

        try {
            log.info("Tracking Order ID : {}",orderId);
            Thread.sleep(500);
            throw new RuntimeException("Error occurred while tracking Order ID : "+orderId);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
