package com.test.oms.order.service;

import com.test.oms.order.models.Availability;
import com.test.oms.order.models.Capacity;
import com.test.oms.order.models.PromiseDate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class DateService {

    public PromiseDate promiseDate;

    public List<Availability> getAvailablityObjects(){
        List<Availability> availabilityList = new ArrayList<>();
        Availability availability1 = new Availability("Store001","Prod1",1.0, new Date(2021,10,29));
        Availability availability2 = new Availability("Store002","Prod2",2.0, new Date(2021,10,26));
        Availability availability3 = new Availability("Store003","Prod3",3.0, new Date(2021,10,25));
        availabilityList.add(availability1);
        availabilityList.add(availability2);
        availabilityList.add(availability3);
        return availabilityList;

    }

    public List<Capacity> getCapacityObjects(){
        List<Capacity> capacityList = new ArrayList<>();
        Capacity capacity1 = new Capacity("Store001","Prod1",1.0, new Date(2021,10,28));
        Capacity capacity2 = new Capacity("Store002","Prod2",2.0, new Date(2021,10,30));
        Capacity capacity3 = new Capacity("Store003","Prod3",3.0, new Date(2021,10,29));
        capacityList.add(capacity1);
        capacityList.add(capacity2);
        capacityList.add(capacity3);
        return capacityList;

    }

    public Date getAvailability(){
        List<Availability> availabilityList = getAvailablityObjects();
        AtomicReference<Availability> actualAvailability = new AtomicReference<>();
        availabilityList.forEach((availability)->{
            if((availability.getStoreNo().equals(promiseDate.getStoreNo()))&&(availability.getProductId().equals(promiseDate.getProductId()))){
                actualAvailability.set(availability);
            }
        });
        return actualAvailability.get().getDate();
    }

    public Date getCapacity(){
        List<Capacity> capacityList = getCapacityObjects();
        AtomicReference<Capacity> actualCapacity = new AtomicReference<>();
        capacityList.forEach((capacity)->{
            if((capacity.getStoreNo().equals(promiseDate.getStoreNo()))&&(capacity.getProductId().equals(promiseDate.getProductId()))){
                actualCapacity.set(capacity);
            }
        });
        return actualCapacity.get().getDate();
    }
    public PromiseDate getAvailabilityDate(PromiseDate promiseDate) throws Exception{
        this.promiseDate=promiseDate;
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Date> future1 = executorService.submit(this::getAvailability);
        Future<Date> future2 = executorService.submit(this::getCapacity);
        Date availabilityDate = future1.get();
        Date capacityDate = future2.get();
        if(availabilityDate.before(capacityDate))
            this.promiseDate.setEDD(capacityDate);
        else
            this.promiseDate.setEDD(availabilityDate);
        return this.promiseDate;
    }
}
