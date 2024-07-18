package com.jaiylonbabb.uprightcleaningservices.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pay")
public class CheckoutController {
    @GetMapping("/checkout")
    public String processCheckout() {
        // Simulate a payment process
        return "checkout";
    }
}
