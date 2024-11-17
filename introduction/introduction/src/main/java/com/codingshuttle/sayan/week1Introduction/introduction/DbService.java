package com.codingshuttle.sayan.week1Introduction.introduction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DbService {

   final private DB db;

    public DbService(DB db) {
        this.db = db;
    }

    void getData(){
        System.out.println(db.getData());
    }
}
