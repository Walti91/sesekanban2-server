package sese.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sese.requests.BillRequest;
import sese.responses.BillResponse;
import sese.responses.PaymentResponse;
import sese.responses.ReminderResponse;
import sese.services.BillService;
import sese.services.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/api/rechnung")
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<BillResponse> getAllBills() {
        return billService.getAll();
    }

    @PostMapping("")
    @CrossOrigin(origins = "http://localhost:4200")
    public void addBill(@RequestBody BillRequest billRequest) {
        billService.addNewBill(billRequest);
    }

    @GetMapping("/{id}")
    public BillResponse getBill(@PathVariable Long id) {
        return billService.getBillById(id);
    }

    @GetMapping("/suche/{keyword}")
    public List<BillResponse> getReservationByKeyword(@PathVariable String keyword) {
        return billService.getBillByKeyword(keyword);
    }

    @GetMapping("/suche")
    public List<BillResponse> getBillWithoutKeyword() {
        return billService.getAll();
    }

    @PostMapping("/{id}/mahnung")
    public ReminderResponse sendRemainder(@PathVariable Long id) {
        return billService.sendReminder(id);
    }

    @GetMapping("/{id}/zahlungsBestaetigung")
    public PaymentResponse sendConfirmation(@PathVariable Long id) {
        return paymentService.sendConfirmation(id);
    }
}
