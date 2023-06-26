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

    public double getPrice(Body b) throws Exception {
        double p = 0;

        // Compute discount for customer
        double d = getCustomerDisount(b.getType());

        if (b.getItems() == null) {
            return 0;
        }

        // Compute total amount depending on the types and quantity of product and
        // if we are in winter or summer discounts periods
        if (isDiscountPeriod()) {
            for (int i = 0; i < b.getItems().length; i++) {
                Item it = b.getItems()[i];

                if (it.getType().equals("TSHIRT")) {
                    p += 30 * it.getNb() * d;
                } else if (it.getType().equals("DRESS")) {
                    p += 50 * it.getNb() * 0.8 * d;
                } else if (it.getType().equals("JACKET")) {
                    p += 100 * it.getNb() * 0.9 * d;
                }
                // else if (it.getType().equals("SWEATSHIRT")) {
                // price += 80 * it.getNb();
                // }
            }

        } else {
            for (int i = 0; i < b.getItems().length; i++) {
                Item it = b.getItems()[i];

                if (it.getType().equals("TSHIRT")) {
                    p += 30 * it.getNb() * d;
                } else if (it.getType().equals("DRESS")) {
                    p += 50 * it.getNb() * d;
                } else if (it.getType().equals("JACKET")) {
                    p += 100 * it.getNb() * d;
                }
                // else if (it.getType().equals("SWEATSHIRT")) {
                // price += 80 * it.getNb();
                // }
            }
        }

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

    private boolean isDiscountPeriod() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);
        return (cal.get(Calendar.DAY_OF_MONTH) < 15 && cal.get(Calendar.DAY_OF_MONTH) > 5
                && cal.get(Calendar.MONTH) == 5)
                || (cal.get(Calendar.DAY_OF_MONTH) < 15 && cal.get(Calendar.DAY_OF_MONTH) > 5
                        && cal.get(Calendar.MONTH) == 0);
    }

}
