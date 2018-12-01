package sese.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sese.entities.Bill;
import sese.entities.Payment;
import sese.exceptions.SeseError;
import sese.exceptions.SeseException;
import sese.repositories.BillRepository;
import sese.repositories.PaymentRepository;
import sese.services.utils.PdfGenerationUtil;
import sese.services.utils.TemplateUtil;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private MailService mailService;

    /**
     * Processes an incoming payment.
     * Throws an exception when there's no bill with id = "billId".
     * @param billId
     */
    @Transactional
    public void processPayment(Long billId){


        Bill bill = billRepository.findById(billId).orElseThrow(() -> new SeseException(SeseError.BILL_ID_NOT_FOUND));

        Payment payment = new Payment();
        payment.setTimestamp(OffsetDateTime.now());
        payment.setValue(bill.getAmount());

        sendPaymentMail(bill,payment);
        payment.setEmailSent(true);

        bill.addPayment(payment);

        billRepository.save(bill);
        paymentRepository.save(payment);
    }

    private void sendPaymentMail(Bill bill, Payment payment) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", bill.getReservations().get(0).getCustomer().getName());

        String htmlText = TemplateUtil.processTemplate("zahlungs_mail", variables);

        variables = new HashMap<>();
        System.out.println(bill.getReservations().toString());
        variables.put("reservations", bill.getReservations());
        variables.put("amount", bill.getAmount());
        byte[] pdfAttachment = PdfGenerationUtil.createPdf("zahlungs_pdf", variables);
        mailService.sendMailWithAttachment("hotelverwaltung@sese.at", bill.getReservations().get(0).getCustomer().getEmail(), "Ihre Zahlung ist eingegangen", htmlText , "bestaetigung.pdf", pdfAttachment, "application/pdf");
    }
}
