package sese.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import sese.entities.Room;
import sese.exceptions.SeseError;
import sese.exceptions.SeseException;
import sese.repositories.RoomRepository;
import sese.requests.RoomRequest;
import sese.responses.RoomResponse;

import javax.swing.text.html.Option;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<RoomResponse> list() {
        List<Room> rooms = roomRepository.findAll();

        if(CollectionUtils.isEmpty(rooms)) {
            throw new SeseException(SeseError.NO_ROOMS);
        }

        return rooms.stream().map(RoomResponse::new).collect(Collectors.toList());
    }

    public RoomResponse getRoomById(Long roomId)
    {
        Optional<Room> roomOptional=roomRepository.findById(roomId);
        Room room;

        if(roomOptional.isPresent())
            room=roomOptional.get();

        else
            throw new SeseException(SeseError.ROOM_NOT_FOUND);

        return new RoomResponse(room);

    }

    public List<RoomResponse> getRoomsByReservationId(Long reservationId)
    {
        List<Room> roomList=roomRepository.findByReservationsId(reservationId);
        List<RoomResponse> responseList=new ArrayList<>();

        for(Room room:roomList)
        {
            responseList.add(new RoomResponse(room));
        }

        return responseList;
    }

    public List<RoomResponse> getFreeRooms(RoomRequest roomRequest) {
        return roomRepository.findAllByFree(roomRequest.getStartDate(), roomRequest.getEndDate()).stream().map(RoomResponse::new).collect(Collectors.toList());
    }
}
