package sese.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sese.entities.*;
import sese.exceptions.SeseError;
import sese.exceptions.SeseException;
import sese.repositories.BillRepository;
import sese.repositories.ReminderRepository;
import sese.repositories.ReservationRepository;
import sese.requests.BillRequest;
import sese.responses.BillResponse;
import sese.responses.ReminderResponse;
import sese.services.utils.BillCostCalculaterUtil;
import sese.services.utils.PdfGenerationUtil;
import sese.services.utils.TemplateUtil;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BillService {

    private BillRepository billRepository;

    private ReservationRepository reservationRepository;

    private ReminderRepository reminderRepository;

    @Autowired
    private MailService mailService;


    @Autowired
    public BillService(ReservationRepository reservationRepository, BillRepository billRepository, ReminderRepository reminderRepository) {
        this.billRepository = billRepository;
        this.reservationRepository = reservationRepository;
        this.reminderRepository = reminderRepository;

    }

    @Transactional
    public ReminderResponse sendReminder(Long id) {

        Bill bill = getBillObjectById(id);

        //dont send reminder if bill is cancelled
        if (!bill.isCancelled()) {
            Reminder reminder = new Reminder();
            reminder.setBill(bill);
            reminder.setTimestamp(OffsetDateTime.now());

            bill.addReminder(reminder);

            Map<String, Object> variablesMail = new HashMap<>();
            variablesMail.put("name", bill.getReservations().get(0).getCustomer().getName());

            String htmlText = TemplateUtil.processTemplate("mahnungs_mail", variablesMail);

            Map<String, Object> variablesPdf = new HashMap<>();
            variablesPdf.put("name", bill.getReservations().get(0).getCustomer().getName());
            variablesPdf.put("bill", bill.getId());
            // bill amount minus all payment amounts for that bill
            variablesPdf.put("amount", bill.getAmount() - bill.getPayments().stream().map(p -> p.getValue()).mapToDouble(Double::doubleValue).sum());

            byte[] pdfAttachment = PdfGenerationUtil.createPdf("mahnungs_pdf", variablesPdf);

            try {
                mailService.sendMailWithAttachment("hotelverwaltung@sese.at", bill.getReservations().get(0).getCustomer().getEmail(), "Mahnung", htmlText, "mahnung.pdf", pdfAttachment, "application/pdf");
                reminder.setEmailSent(true);
            } catch (Exception e) {
                reminder.setEmailSent(false);
            }

            Reminder saved = reminderRepository.save(reminder);
            billRepository.save(bill);

            return new ReminderResponse(saved);
        }else{
            //didnt send a reminder, so there is no reminder to return
            return null;
        }
    }

    public BillResponse addNewBill(BillRequest billRequest) {

        if (billRequest == null) {
            throw new IllegalArgumentException("Billrequest must not be null");
        }

        if (billRequest.getId() != null) {
            throw new IllegalArgumentException("Bill must be null");
        }

        List<Long> reservationIds = billRequest.getReservationIds();
        if (reservationIds == null || reservationIds.isEmpty()) {
            throw new IllegalArgumentException("Reservations of bill must not be null or empty");
        }

        double amount = 0.0;

        List<Reservation> reservations = new ArrayList<>();
        for (Long reservationId : reservationIds) {

            Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);

            if (!reservationOptional.isPresent()) {
                throw new SeseException(SeseError.BILL_INVALID_RESERVATION);
            }
            Reservation reservation = reservationOptional.get();
            reservations.add(reservation);

            amount += BillCostCalculaterUtil.calculate(reservation);
        }

        Bill bill = new Bill();
        bill.setAmount(amount);
        bill.setCancelled(false);
        bill.setReservations(reservations);

        Reminder reminder = new Reminder();
        reminder.setBill(bill);

        OffsetDateTime latestDate = OffsetDateTime.now();
        for (Reservation reservation : reservations) {
            OffsetDateTime resDate = reservation.getEndDate();
            if (resDate.isAfter(latestDate)) {
                latestDate = resDate;
            }
        }
        reminder.setTimestamp(latestDate.plusWeeks(1));//reminder default is 1 week after reservation end (or now if that is later)
        reminder.setEmailSent(false);

        bill.addReminder(reminder);

        billRepository.save(bill);

        reminderRepository.save(reminder);

        reservations.stream().forEach(reservation -> {

            //if this reservation had another bill, cancel the old bill
            Bill oldBill = reservation.getBill();
            if (oldBill != null && oldBill != bill) {
                oldBill.setCancelled(true);
                billRepository.save(oldBill);
            }

            reservation.setBill(bill);
            reservationRepository.save(reservation);
        });

        sendBillMail(bill);

        return new BillResponse(bill);
    }

    private void sendBillMail(Bill bill) {
        Reservation reservation = bill.getReservations().stream().findFirst().orElseThrow(() -> new SeseException(SeseError.RESERVATION_NOT_FOUND));
        double betrag = bill.getReservations().stream()
                .map(r -> r.getRoomReservations().stream().map(rr -> rr.getRoom().getPriceAdult() * rr.getAdults() + rr.getRoom().getPriceChild() * rr.getChildren()).reduce(0D, (a, b) -> a + b))
                .reduce(0D, (a, b) -> a + b);
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", reservation.getCustomer().getName());
        variables.put("rechnungsnummer", bill.getId());
        variables.put("betrag", betrag);
        String htmlText = TemplateUtil.processTemplate("rechnungs_mail", variables);
        byte[] pdfAttachment = PdfGenerationUtil.createPdf("rechnungs_pdf", variables);
        mailService.sendMailWithAttachment("hotelverwaltung@sese.at", reservation.getCustomer().getEmail(), "Ihre Rechnung!", htmlText, "rechnung.pdf", pdfAttachment, "application/pdf");
    }


    private Bill getBillObjectById(Long billId) {
        Optional<Bill> billOptional = billRepository.findById(billId);
        Bill bill;

        if (billOptional.isPresent()) {
            bill = billOptional.get();
        } else {
            throw new SeseException(SeseError.BILL_ID_NOT_FOUND);
        }

        return bill;
    }


    public BillResponse getBillById(Long billId) {
        return new BillResponse(getBillObjectById(billId));
    }

    public List<BillResponse> getAll() {
        List<Bill> bills = billRepository.findAll();

        /*if(CollectionUtils.isEmpty(customers)) {
            throw new SeseException(SeseError.NO_CUSTOMER);
        }*/

        System.out.println(bills.toString());

        return bills.stream().map(BillResponse::new).collect(Collectors.toList());
    }

    public List<BillResponse> getBillByKeyword(String keyword) {
        List<BillResponse> bills = new ArrayList<>();
        try {
            Optional<Bill> billOptional = billRepository.findById(Long.parseLong(keyword));
            if (billOptional.isPresent()) {
                bills.add(new BillResponse(billOptional.get()));
                return bills;
            }
        } catch (NumberFormatException e) {
            //not a number continue below
        }

        List<Reservation> reservations = reservationRepository.findByCustomerNameContaining(keyword);

        for (Reservation reservation : reservations) {
            bills.add(new BillResponse(reservation.getBill()));
        }

        //if empty search in date
        if (bills.isEmpty()) {
            reservations = reservationRepository.findAll();
            for (Reservation reservation : reservations) {

                if (reservation.getStartDate().toString().contains(keyword) || reservation.getEndDate().toString().contains(keyword)) {
                    bills.add(new BillResponse(reservation.getBill()));
                }
            }
        }

        return bills;

    }


}
