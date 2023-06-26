package com.sipios.refactoring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sipios.refactoring.service.ShoppingService;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    // FIXME Use injection
    private ShoppingService shoppingService = new ShoppingService();

    @PostMapping
    public String getPrice(@RequestBody Body b) {

        try {
            // TODO convert Body to "metier" object
            return String.valueOf(shoppingService.getPrice(b));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
