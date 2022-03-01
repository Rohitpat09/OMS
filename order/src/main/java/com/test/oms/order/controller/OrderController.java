package com.test.oms.order.controller;

import com.test.oms.order.models.PromiseDate;
import com.test.oms.order.service.DateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class OrderController {

    public final DateService dateService;

    public OrderController(DateService dateService){
        this.dateService = dateService;
    }

    @GetMapping(value = "/getDatesByService")
    public PromiseDate getDatesByService(@RequestBody PromiseDate promiseDate) throws Exception{
        return dateService.getAvailabilityDate(promiseDate);

    }
}
