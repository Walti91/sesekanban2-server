package sese.services;

import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;
import sese.entities.Bill;
import sese.entities.Payment;
import sese.entities.Reminder;
import sese.entities.Reservation;
import sese.repositories.BillRepository;
import sese.repositories.ReminderRepository;
import sese.services.utils.TemplateUtil;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReminderService implements Runnable {


    private BillRepository billRepository;
    private ReminderRepository reminderRepository;
    private MailService mailService;

    public ReminderService(BillRepository billRepository, ReminderRepository reminderRepository, MailService mailService) {
        this.billRepository = billRepository;
        this.reminderRepository = reminderRepository;
        this.mailService = mailService;
    }


    //Check every week if there are any reminders to be sent. And send them
    @Transactional(readOnly=true)
    public void run() {

        List<Bill> bills = billRepository.findAll();

        while (true) {
            for (Bill bill : bills) {

                Hibernate.initialize(bill.getPayments());

                List<Payment> payments= bill.getPayments();

                if (payments == null || payments.isEmpty()) {//no payment yet

                    List<Reminder> reminders = bill.getReminders();
                    if (reminders == null || reminders.isEmpty()) {//no reminders, add one
                        addReminderNow(bill);
                    }

                    boolean allRemindersSentNoPayment = true;

                    //reminders are created during bill creation. There is usually one reminder.
                    for (Reminder reminder : bill.getReminders()) {
                        if (!reminder.isEmailSent() && reminder.getTimestamp().isBefore(OffsetDateTime.now())) {
                            allRemindersSentNoPayment = false;
                            sendReminderMail(reminder);
                        }
                    }

                    if (allRemindersSentNoPayment) {//the customer still hasn't paid after reminders, send another one
                        Reminder reminder = addReminderNow(bill);
                        sendReminderMail(reminder);
                    }

                }


            }


            try {
                Thread.sleep(604800000);//1 week
//                Thread.sleep(10000);//10 secs(for testing)
            } catch (InterruptedException e) {
                //shouldnt happen
            }
        }

    }

    private void sendReminderMail(Reminder reminder) {

        Map<String, Object> variables = new HashMap<>();
        variables.put("id", reminder.getBill().getId());

        OffsetDateTime latestDate = OffsetDateTime.now();
        for (Reservation reservation : reminder.getBill().getReservations()) {
            OffsetDateTime resDate = reservation.getEndDate();
            if (resDate.isAfter(latestDate)) {
                latestDate = resDate;
            }
        }

        variables.put("date", latestDate.plusWeeks(2));//default: pay 2 weeks after last reservation

        String htmlText = TemplateUtil.processTemplate("erinnerungs_mail", variables);

        mailService.sendMail("hotelverwaltung@sese.at", reminder.getBill().getReservations().get(0).getCustomer().getEmail(), "Erinnerung", htmlText);
    }


    private Reminder addReminderNow(Bill bill) {

        Reminder reminder = new Reminder();
        reminder.setTimestamp(OffsetDateTime.now());//now
        reminder.setBill(bill);
        reminder.setEmailSent(false);

        bill.addReminder(reminder);

        reminderRepository.save(reminder);

        return reminder;

    }
}
