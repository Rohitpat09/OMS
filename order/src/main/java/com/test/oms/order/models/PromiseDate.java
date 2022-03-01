package com.test.oms.order.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class PromiseDate {
    String storeNo;
    String productId;
    Integer reqQty;
    Date EDD;
}
