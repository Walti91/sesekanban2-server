package sese.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sese.entities.Bill;
import sese.repositories.BillRepository;
import sese.services.utils.TemplateUtil;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RememberService {

    private BillRepository billRepository;
    private MailService mailService;

    public RememberService(BillRepository billRepository, MailService mailService) {
        this.billRepository = billRepository;
        this.mailService = mailService;
    }

    /*
    Watch out, logic just works if task is running every 24 hours
     */
    @Scheduled(fixedRate = 86400000)
    @Transactional(readOnly = true)
    public void remind() {
        List<Bill> bills = billRepository.findAll();

        bills.stream()
                .filter(b -> !b.isPaid() && b.getCreated().plusWeeks(1L).getDayOfYear() == OffsetDateTime.now().getDayOfYear() && b.getCreated().plusWeeks(1L).getYear() == OffsetDateTime.now().getYear())
                .forEach(this::sendRememberMail);
    }

    private void sendRememberMail(Bill bill) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", bill.getId());

        variables.put("date", OffsetDateTime.now().plusWeeks(1L));

        String htmlText = TemplateUtil.processTemplate("erinnerungs_mail", variables);

        mailService.sendMail("hotelverwaltung@sese.at", bill.getReservations().get(0).getCustomer().getEmail(), "Erinnerung", htmlText);
    }
}
