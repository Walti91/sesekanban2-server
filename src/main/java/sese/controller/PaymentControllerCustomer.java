package sese.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sese.requests.PaymentRequest;
import sese.services.PaymentService;


// UNFORTUNATELY DID I SEND THE CUSTOMER THE INTERFACE SPECIFICATION FOR THE PAYMENTS WITHOUT THE "api" PREFIX IN
// OUR URLs... THEREFORE IT ISN'T USED HERE
@RestController
@RequestMapping("/rechnung")
public class PaymentControllerCustomer {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/{id}/zahlungseingang")
    public void processPayment(@PathVariable Long id) {
        paymentService.processPayment(id);
    }
}
