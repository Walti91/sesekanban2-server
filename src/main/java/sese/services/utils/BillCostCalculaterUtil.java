package sese.services.utils;

import sese.entities.Reservation;
import sese.entities.Room;
import sese.entities.RoomReservation;

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

public final class BillCostCalculaterUtil {

    public static double calculate(Reservation reservation) {

        List<RoomReservation> roomReservations = reservation.getRoomReservations();

        double total = 0.0;

        long days = ChronoUnit.DAYS.between(reservation.getStartDate(), reservation.getEndDate());


        for (RoomReservation roomReservation : roomReservations) {
            int adults = roomReservation.getAdults();
            int children = roomReservation.getChildren();
            Room room = roomReservation.getRoom();

            double adultPriceTotal = room.getPriceAdult() * adults;
            double childrenPriceTotal = room.getPriceChild() * children;

            int pensionPricePerDay;
            //full pension = 3 meals
            //half pension = 2 meals
            //breakfast = 1 meal
            //5 euro per meal
            switch (roomReservation.getPension()) {
                case FULL:
                    pensionPricePerDay = 15;
                    break;
                case HALF:
                    pensionPricePerDay = 10;
                    break;
                case BREAKFAST:
                    pensionPricePerDay = 5;
                    break;
                default:
                    //default is breakfast, shouldnt come here
                    pensionPricePerDay = 5;
                    break;

            }

            double pensionTotal = (adults + children) * days * pensionPricePerDay;

            total += adultPriceTotal + childrenPriceTotal + pensionTotal;

        }

        int discount = reservation.getCustomer().getDiscount();
        total =  total - ((discount*total)/100);

        return total;
    }
}
