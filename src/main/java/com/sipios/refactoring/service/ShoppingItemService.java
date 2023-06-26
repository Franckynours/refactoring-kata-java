package com.sipios.refactoring.service;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sipios.refactoring.controller.Item;

public class ShoppingItemService {

    private Logger logger = LoggerFactory.getLogger(ShoppingItemService.class);

    public double getUnitPrice(Item i, Calendar cal) throws Exception {
        boolean isDiscountPeriod = isDiscountPeriod(cal);
        switch (i.getType()) {
        case "TSHIRT":
            return 30;
        case "DRESS":
            return 50 * (isDiscountPeriod ? 0.8 : 1);
        case "JACKET":
            return 100 * (isDiscountPeriod ? 0.9 : 1);
        default:
            throw new Exception("Unknown product");
        }
    }

    public double getPrice(Item i, Calendar cal) throws Exception {
        return i.getNb() * getUnitPrice(i, cal);
    }

    private boolean isDiscountPeriod(Calendar cal) {
        return (cal.get(Calendar.DAY_OF_MONTH) < 15 && cal.get(Calendar.DAY_OF_MONTH) > 5
                && cal.get(Calendar.MONTH) == 5)
                || (cal.get(Calendar.DAY_OF_MONTH) < 15 && cal.get(Calendar.DAY_OF_MONTH) > 5
                        && cal.get(Calendar.MONTH) == 0);
    }

}
