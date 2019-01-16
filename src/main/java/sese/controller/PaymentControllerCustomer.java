package sese.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sese.exceptions.SeseError;
import sese.exceptions.SeseException;
import sese.requests.PaymentRequest;
import sese.responses.PaymentResponse;
import sese.services.PaymentService;

import java.util.Objects;


// UNFORTUNATELY DID I SEND THE CUSTOMER THE INTERFACE SPECIFICATION FOR THE PAYMENTS WITHOUT THE "api" PREFIX IN
// OUR URLs... THEREFORE IT ISN'T USED HERE
@RestController
@RequestMapping("/rechnung")
public class PaymentControllerCustomer {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/{billId}/zahlungseingang")
    public PaymentResponse processPayment(@PathVariable(name = "billId") Long billId, @RequestBody PaymentRequest paymentRequest) {
        if (Objects.isNull(paymentRequest.getAmount())) {
            throw new SeseException(SeseError.PAYMENT_AMOUNT_NOT_GIVEN);
        }

        return paymentService.processPayment(billId, paymentRequest.getAmount());
    }

    @GetMapping("/zahlung/{paymentId}/mail")
    public PaymentResponse sendPaymentMail(@PathVariable(name = "paymentId") Long paymentId) {
        return paymentService.sendPaymentMail(paymentId);
    }
}
