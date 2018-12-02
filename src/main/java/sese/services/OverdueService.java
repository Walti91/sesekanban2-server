package sese.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sese.entities.Bill;
import sese.entities.Payment;
import sese.entities.Reservation;
import sese.repositories.BillRepository;
import sese.services.utils.PdfGenerationUtil;
import sese.services.utils.TemplateUtil;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OverdueService {

    private BillRepository billRepository;
    private MailService mailService;

    public OverdueService(BillRepository billRepository, MailService mailService) {
        this.billRepository = billRepository;
        this.mailService = mailService;
    }

    //check everyday if anyone missed their deadlines and send overdue notice
    @Scheduled(fixedRate = 86400000)
    @Transactional(readOnly = true)
    public void run() {
        List<Bill> bills = billRepository.findAll();
        for (Bill bill : bills) {

            List<Payment> payments = bill.getPayments();

            if (payments == null || payments.isEmpty()) {

                OffsetDateTime latestDate = OffsetDateTime.now();
                for (Reservation reservation : bill.getReservations()) {
                    OffsetDateTime resDate = reservation.getEndDate();
                    if (resDate.isAfter(latestDate)) {
                        latestDate = resDate;
                    }
                }

                if (OffsetDateTime.now().isAfter(latestDate.plusWeeks(2))) {
                    sendOverdueMail(bill);
                }
            }
        }
    }

    private void sendOverdueMail(Bill bill) {

        Map<String, Object> variables = new HashMap<>();
        variables.put("name", bill.getReservations().get(0).getCustomer().getName());

        String htmlText = TemplateUtil.processTemplate("zahlungs_mail", variables);

        variables = new HashMap<>();
        System.out.println(bill.getReservations().toString());
        variables.put("bill", bill.getId());
        variables.put("amount", bill.getAmount() * 0.05);//5% of the bill amoun
        byte[] pdfAttachment = PdfGenerationUtil.createPdf("zahlungs_pdf", variables);
        mailService.sendMailWithAttachment("hotelverwaltung@sese.at", bill.getReservations().get(0).getCustomer().getEmail(), "Mahnung", htmlText, "mahnung.pdf", pdfAttachment, "application/pdf");
    }

}
