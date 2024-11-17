package com.codingshuttle.sayan.week1Introduction.introduction;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class Apple {
    void eatApple(){
        System.out.println("I am eating the Apple");
    }

    @PostConstruct
    public void callThisBeforeAppleIsUsed(){
        System.out.println("Calling this before Apple is used");
    }

    @PreDestroy
    public void callThisBeforeDestroy(){
        System.out.println("Calling this before Apple is destroyed");
    }
}
