package com.sipios.refactoring.service;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sipios.refactoring.controller.Body;
import com.sipios.refactoring.controller.Item;

public class ShoppingService {

    private Logger logger = LoggerFactory.getLogger(ShoppingService.class);
    private ShoppingItemService shoppingItemService = new ShoppingItemService();

    public double getPrice(Body b) throws Exception {
        // Compute discount for customer
        double d = getCustomerDisount(b.getType());

        if (b.getItems() == null) {
            return 0;
        }

        Date date = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);

        double p = 0;
        for (Item item : b.getItems()) {
            p += shoppingItemService.getPrice(item, cal);
        }

        // Apply customer discount
        p *= d;

        checkCustomerMaxPriceCart(b.getType(), p);
        return p;
    }

    // Compute discount for customer
    private double getCustomerDisount(String customerType) throws Exception {
        if (customerType.equals("STANDARD_CUSTOMER")) {
            return 1;
        } else if (customerType.equals("PREMIUM_CUSTOMER")) {
            return 0.9;
        } else if (customerType.equals("PLATINUM_CUSTOMER")) {
            return 0.5;
        } else {
            throw new Exception("Customer type unknown");
        }
    }

    private void checkCustomerMaxPriceCart(String customerType, double cartPrice) throws Exception {
        if (customerType.equals("STANDARD_CUSTOMER")) {
            if (cartPrice > 200) {
                throw new Exception("Price (" + cartPrice + ") is too high for standard customer");
            }
        } else if (customerType.equals("PREMIUM_CUSTOMER")) {
            if (cartPrice > 800) {
                throw new Exception("Price (" + cartPrice + ") is too high for premium customer");
            }
        } else if (customerType.equals("PLATINUM_CUSTOMER")) {
            if (cartPrice > 2000) {
                throw new Exception("Price (" + cartPrice + ") is too high for platinum customer");
            }
        } else {
            if (cartPrice > 200) {
                throw new Exception("Price (" + cartPrice + ") is too high for standard customer");
            }
        }
    }

}
