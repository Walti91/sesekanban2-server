package sese.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sese.requests.RoomRequest;
import sese.responses.RoomFreeResponse;
import sese.responses.RoomResponse;
import sese.services.RoomService;

import java.util.List;

@RestController
@RequestMapping("/api/zimmer")
public class RoomController {

    private RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("")
    public List<RoomResponse> list() {
        return roomService.list();
    }

    @GetMapping("/{roomId}")
    public RoomResponse getRoomById(@PathVariable Long roomId)
    {
        return roomService.getRoomById(roomId);
    }

    @GetMapping("/reservation/{reservationId}")
    public List<RoomResponse> getRoomsByrReservationId(@PathVariable Long reservationId)
    {
        return roomService.getRoomsByReservationId(reservationId);
    }

    @PostMapping("free")
    public List<RoomResponse> getFreeRooms(@RequestBody RoomRequest roomRequest) {
        return roomService.getFreeRooms(roomRequest);
    }

    @PostMapping("free/{roomId}")
    public RoomFreeResponse isRoomFree(@PathVariable Long roomId, @RequestBody RoomRequest roomRequest) {
        return roomService.isRoomFree(roomId, roomRequest);
    }
}
