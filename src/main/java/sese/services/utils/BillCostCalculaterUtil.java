package sese.services.utils;

import sese.entities.Reservation;
import sese.entities.Room;
import sese.entities.RoomReservation;

import java.time.temporal.ChronoUnit;
import java.util.List;

public final class BillCostCalculaterUtil {

    public static double calculate(Reservation reservation) {

        List<RoomReservation> roomReservations = reservation.getRoomReservations();

        double total = 0.0;

        for (RoomReservation roomReservation : roomReservations) {

            long days = ChronoUnit.DAYS.between(roomReservation.getStartDate(), roomReservation.getEndDate());

            Room room = roomReservation.getRoom();

            double priceAdultPerDay = room.getPriceAdult();
            double priceChildPerDay = room.getPriceChild();

            switch (roomReservation.getPension()) {
                case FULL:
                    priceAdultPerDay += 60;
                    priceChildPerDay += 45;
                    break;
                case HALF:
                    priceAdultPerDay += 35;
                    priceChildPerDay += 25;
                    break;
                case BREAKFAST:
                    priceAdultPerDay += 15;
                    priceChildPerDay += 12;
                    break;
            }

            total += priceAdultPerDay * days + priceChildPerDay * days;
        }

        int discount = reservation.getCustomer().getDiscount();
        total =  total - ((discount*total)/100);

        return total;
    }
}
