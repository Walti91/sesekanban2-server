package sese.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sese.entities.Customer;
import sese.entities.Reservation;
import sese.entities.Room;
import sese.repositories.CustomerRepository;
import sese.repositories.ReservationRepository;
import sese.repositories.RoomRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class TestService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;
/*
    public void addTestData() {
        Customer customer = new Customer();
        customer.setName("Testname");

        Customer savedC = customerRepository.save(customer);
        System.out.println("CustomerId: " + savedC.getId());

        Room savedR = roomRepository.findAll().get(0);

        Reservation reservation = new Reservation();
        reservation.setCustomer(savedC);
        Set<Room> roomSet = new HashSet<>();
        roomSet.add(savedR);
        reservation.setRoomReservations(roomSet);

        Reservation savedRes = reservationRepository.save(reservation);

        System.out.println("Reservation-Id: " + savedRes.getId());


    }
    */
}
