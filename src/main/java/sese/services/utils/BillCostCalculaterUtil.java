package sese.services.utils;

import sese.entities.Reservation;
import sese.entities.Room;
import sese.entities.RoomReservation;

import java.util.List;

public final class BillCostCalculaterUtil {

    public static double calculate(Reservation reservation) {

        List<RoomReservation> roomReservations = reservation.getRoomReservations();

        double total = 0.0;

        for (RoomReservation roomReservation : roomReservations) {
            int adults = roomReservation.getAdults();
            int children = roomReservation.getChildren();
            Room room = roomReservation.getRoom();

            double adultPriceTotal = room.getPriceAdult() * adults;
            double childrenPriceTotal = room.getPriceChild() * children;

            total += adultPriceTotal + childrenPriceTotal;
        }

        return total;
    }
}
