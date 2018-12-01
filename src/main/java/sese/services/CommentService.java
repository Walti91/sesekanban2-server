package sese.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sese.entities.Comment;
import sese.entities.Reservation;
import sese.exceptions.SeseError;
import sese.exceptions.SeseException;
import sese.repositories.ReservationRepository;
import sese.requests.CommentRequest;
import sese.responses.CommentResponse;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Transactional
    public void addComment(Long reservationId, CommentRequest commentRequest) {

        if (Objects.isNull(commentRequest) || Objects.isNull(commentRequest.getText()) || commentRequest.getText().isEmpty()) {
            throw new SeseException(SeseError.COMMENT_TEXT_NULL_OR_EMPTY);
        }

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new SeseException(SeseError.RESERVATION_NOT_FOUND));

        Comment comment = new Comment();
        comment.setText(commentRequest.getText());

        reservation.addComment(comment);
    }

    public List<CommentResponse> getCommentsForReservation(Long reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new SeseException(SeseError.RESERVATION_NOT_FOUND));

        return reservation.getComments().stream().map(CommentResponse::new).collect(Collectors.toList());
    }
}
