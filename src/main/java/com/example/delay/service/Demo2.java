package com.example.delay.service;

import com.example.delay.service.intef.demo;
import org.springframework.stereotype.Service;

@Service
public class Demo2 implements demo {

    @Override
    public void isTrue() {
        System.out.println("false");
    }
}
