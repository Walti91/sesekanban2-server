package sese.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sese.requests.BillRequest;
import sese.responses.BillPdfResponse;
import sese.responses.BillResponse;
import sese.responses.ReminderResponse;
import sese.services.BillService;

import java.util.List;

@RestController
@RequestMapping("/api/rechnung")
public class BillController {

    @Autowired
    private BillService billService;

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

    @GetMapping("/{id}/pdf")
    public BillPdfResponse getBillPdf(@PathVariable Long id) {
        return billService.getBillPdfForBill(id);
    }

    @GetMapping("/overdue")
    public List<BillResponse> getOverdueBills()
    {
        return billService.getOverdueBills();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/{id}/storno")
    public BillResponse cancelBill(@PathVariable Long id)
    {
        return billService.cancelBill(id);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/{id}/rabatt/{discount}")
    public BillResponse updateBillDiscount(@PathVariable Long id, @PathVariable int discount)
    {
        return billService.updateBillDiscount(id,discount);
    }
}
