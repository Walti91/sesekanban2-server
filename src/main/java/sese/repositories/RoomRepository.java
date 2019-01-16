package sese.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sese.entities.Room;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Set<Room> findAllByIdIn(List<Long> ids);
    List<Room> findByReservationsId(Long id);

    //@Query("select room from Room room where room.id not in (select reservation.roomReservation.room.id from Reservation where reservation.startDate < :startDate and reservation.endDate < :endDate)")
    //@Query("select room from Room room left outer join room.reservations roomReservation left outer join roomReservation.reservation reservation where roomReservation is null or reservation.endDate < :startDate or reservation.endDate < :startDate")
    //@Query("select room from Room room left outer join room.reservations reservation where reservation is null")


    //@Query(value = "select room from room where room.id not in (select roomReservation.room.id from room_reservation rr join reservation r on rr.roomId = r.id where r.startDate > :endDate or roomReservation.reservation.endDate < :startDate)", nativeQuery = true)
    @Query("select room from Room room where room not in (select room from Room room join room.reservations reservation where reservation.reservation.startDate <= :startDate and reservation.reservation.endDate >= :endDate)")
    List<Room> findAllByFree(@Param("startDate")OffsetDateTime startDate, @Param("endDate")OffsetDateTime endDate);

    @Query("select room from Room room where room.id = :roomId and room not in (select room from Room room join room.reservations reservation where room.id = :roomId and ((:startDate between reservation.reservation.startDate and reservation.reservation.endDate) or (:endDate between reservation.reservation.startDate and reservation.reservation.endDate) or (:startDate <= reservation.reservation.startDate and :endDate >= reservation.reservation.endDate)))")
    Room isRoomFree(@Param("roomId") Long roomId, @Param("startDate") OffsetDateTime startDate, @Param("endDate") OffsetDateTime endDate);
}
