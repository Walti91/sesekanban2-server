package sese.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sese.entities.*;
import sese.repositories.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DataLoader implements ApplicationRunner {

    private RoomRepository roomRepository;
    private CustomerRepository customerRepository;
    private ReservationRepository reservationRepository;
    private BillRepository billRepository;
    private ReminderRepository reminderRepository;
    private MailService mailService;
    private PaymentRepository paymentRepository;

    @Autowired
    public DataLoader(RoomRepository roomRepository, CustomerRepository customerRepository, ReservationRepository reservationRepository, BillRepository billRepository, PaymentRepository paymentRepository, ReminderRepository reminderRepository, MailService mailService) {
        this.roomRepository = roomRepository;
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
        this.billRepository = billRepository;
        this.reminderRepository = reminderRepository;
        this.mailService = mailService;
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        loadRooms();
        loadReservations();
        //bills need to come after reservations cause it depends on it
        loadBills();
    }

    private void loadRooms() {
        Room room = new Room();
        room.setName("Suite 1");
        room.setCapacity(4);
        room.setCapacityAdults(4);
        room.setPriceAdult(180.0);
        room.setPriceChild(120.0);
        roomRepository.save(room);

        room = new Room();
        room.setName("Suite 2");
        room.setCapacity(4);
        room.setCapacityAdults(2);
        room.setPriceAdult(160.0);
        room.setPriceChild(110.0);
        roomRepository.save(room);

        room = new Room();
        room.setName("Standard 1");
        room.setCapacity(4);
        room.setCapacityAdults(2);
        room.setPriceAdult(120.0);
        room.setPriceChild(90.0);
        roomRepository.save(room);

        room = new Room();
        room.setName("Standard 2");
        room.setCapacity(4);
        room.setCapacityAdults(2);
        room.setPriceAdult(120.0);
        room.setPriceChild(90.0);
        roomRepository.save(room);

        room = new Room();
        room.setName("Standard 3");
        room.setCapacity(3);
        room.setCapacityAdults(2);
        room.setPriceAdult(120.0);
        room.setPriceChild(90.0);
        roomRepository.save(room);
    }

    private void loadReservations() {
        Customer customer = new Customer();
        customer.setBillingAddress("Adresse 1");
        customer.setCompanyName("Firma 1");
        customer.setDiscount(10);
        customer.setFax("Fax 1");
        customer.setNote("Note 1");
        customer.setWeb("Web 1");
        customer.setBirthday(new Date());
        customer.setEmail("test1@test.at");
        customer.setGender(Gender.FEMALE);
        customer.setName("Name1");
        customer.setTelephoneNumber("number 1");

        Reservation reservation = new Reservation();
        reservation.setStartDate(OffsetDateTime.now());
        reservation.setEndDate(OffsetDateTime.now().plusWeeks(1));
        reservation.setCustomer(customer);

        Room room = roomRepository.findById(3L).get();

        List<RoomReservation> roomReservations = new ArrayList<>();
        RoomReservation roomReservation = new RoomReservation();
        roomReservation.setAdults(2);
        roomReservation.setChildren(2);
        roomReservation.setRoom(room);
        roomReservation.setReservation(reservation);
        roomReservations.add(roomReservation);

        room.setReservations(roomReservations);
        reservation.setRoomReservations(roomReservations);

        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        customer.setReservations(reservations);

        customerRepository.save(customer);
        reservationRepository.save(reservation);
        roomRepository.save(room);
    }

    private void loadBills() {//todo remove comments
        Bill bill = new Bill();

        Payment payment = new Payment();
        payment.setBill(bill);
        payment.setEmailSent(true);

        payment.setTimestamp(OffsetDateTime.now());
        payment.setValue(10.0);
        bill.setAmount(10.0);

        bill.addPayment(payment);

        List<Reservation> reservations = reservationRepository.findAll();
        bill.setReservations(reservations);

        Reminder reminder = new Reminder();
        reminder.setBill(bill);
        reminder.setEmailSent(false);
        reminder.setTimestamp(OffsetDateTime.now().plusWeeks(1));//default 1 week

        bill.addReminder(reminder);

        billRepository.save(bill);

        reminderRepository.save(reminder);

        reservations.stream().forEach(reservation -> {
            reservation.setBill(bill);
            reservationRepository.save(reservation);
        });

        paymentRepository.save(payment);
    }


}
