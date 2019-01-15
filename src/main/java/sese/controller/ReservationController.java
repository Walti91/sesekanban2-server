package sese.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sese.requests.CommentRequest;
import sese.requests.ReservationRequest;
import sese.responses.CommentResponse;
import sese.responses.ReservationResponse;
import sese.services.*;

import java.util.List;

import java.util.List;

@RestController
@RequestMapping("/api/reservierung")
public class ReservationController {

    private ReservationService reservationService;

    @Autowired
    private CommentService commentService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ReservationResponse createReservation(@RequestBody ReservationRequest reservationRequest) {
        return reservationService.createReservation(reservationRequest);
    }

    @GetMapping()
    public List<ReservationResponse> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{reservationId}")
    public ReservationResponse getReservationById(@PathVariable Long reservationId)
    {
        return reservationService.getReservationById(reservationId);
    }

    @PostMapping("/{reservationId}/kommentar")
    public void createCommentForReservation(@PathVariable Long reservationId, @RequestBody CommentRequest commentRequest) {
        commentService.addComment(reservationId, commentRequest);
    }

    @GetMapping("/{reservationId}/kommentar")
    public List<CommentResponse> getCommentsForReservation(@PathVariable Long reservationId) {
        return commentService.getCommentsForReservation(reservationId);
    }
    @GetMapping("/suche/{keyword}")
    public List<ReservationResponse> getReservationByKeyword(@PathVariable String keyword)
    {
        return reservationService.getReservationByKeyword(keyword);
    }
    @GetMapping("/suche")
    public List<ReservationResponse> getReservationWithoutKeyword()
    {
        return reservationService.getAllReservations();
    }

    @GetMapping("/starting")
    public List<ReservationResponse> getTodaysStartingReservations()
    {
        return reservationService.getTodaysStartingReservations();
    }

    @GetMapping("/ending")
    public List<ReservationResponse> getTodaysEndingReservations()
    {
        return reservationService.getTodaysEndingReservations();
    }
}
