package sese.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sese.entities.Bill;
import sese.entities.Customer;
import sese.entities.Payment;
import sese.entities.Reservation;
import sese.exceptions.SeseError;
import sese.exceptions.SeseException;
import sese.repositories.BillRepository;
import sese.repositories.PaymentRepository;
import sese.responses.PaymentResponse;
import sese.services.utils.LogUtil;
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

    @Autowired
    private LogUtil logUtil;

    /**
     * Processes an incoming payment.
     * Throws an exception when there's no bill with id = "billId".
     * @param billId
     */
    @Transactional
    public PaymentResponse processPayment(Long billId, double amount){
        Bill bill = billRepository.findById(billId).orElseThrow(() -> new SeseException(SeseError.BILL_ID_NOT_FOUND));

        Payment payment = new Payment();
        payment.setTimestamp(OffsetDateTime.now());
        payment.setValue(amount);

        bill.addPayment(payment);
        paymentRepository.save(payment);

        return new PaymentResponse(payment);
    }

    @Transactional
    public PaymentResponse sendPaymentMail(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new SeseException(SeseError.PAYMENT_NOT_FOUND));
        Bill bill = payment.getBill();

        Reservation reservation = bill.getReservations().stream().findAny().orElseThrow(() -> new SeseException(SeseError.RESERVATION_NOT_FOUND));
        Customer customer = reservation.getCustomer();
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", customer.getName());

        String htmlText = TemplateUtil.processTemplate("zahlungs_mail", variables);

        variables = new HashMap<>();
        System.out.println(bill.getReservations().toString());
        variables.put("billid", bill.getId());
        variables.put("amount", payment.getValue());
        byte[] pdfAttachment = PdfGenerationUtil.createPdf("zahlungs_pdf", variables);
        mailService.sendMailWithAttachment("hotelverwaltung@sese.at", customer.getEmail(), "Ihre Zahlung ist eingegangen", htmlText , "zahlungsbestaetigung.pdf", pdfAttachment, "application/pdf");

        payment.setEmailSent(true);

        logUtil.logAction("Eine Zahlungsbestätigung für die Zahlung mit der Id '" + payment.getId() + "' wurde für die Rechnung mit der Id '" + bill.getId() + "' versendet.");

        return new PaymentResponse(payment);
    }
}
