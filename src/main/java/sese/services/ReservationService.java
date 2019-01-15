package sese.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import sese.entities.Customer;
import sese.entities.Reservation;
import sese.entities.Room;
import sese.entities.RoomReservation;
import sese.exceptions.SeseError;
import sese.exceptions.SeseException;
import sese.repositories.CustomerRepository;
import sese.repositories.ReservationRepository;
import sese.repositories.RoomRepository;
import sese.requests.ReservationRequest;
import sese.requests.RoomReservationRequest;
import sese.responses.ReservationResponse;
import sese.services.utils.PdfGenerationUtil;
import sese.services.utils.TemplateUtil;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private CustomerRepository customerRepository;
    private ReservationRepository reservationRepository;
    private RoomRepository roomRepository;
    private CustomerService customerService;
    private ModelMapper modelMapper;
    private MailService mailService;

    @Autowired
    private LogService logService;

    @Autowired
    public ReservationService(CustomerRepository customerRepository, CustomerService customerService, ModelMapper modelMapper, RoomRepository roomRepository, ReservationRepository reservationRepository, MailService mailService) {
        this.customerRepository = customerRepository;
        this.customerService = customerService;
        this.modelMapper = modelMapper;
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.mailService = mailService;
    }

    @Transactional
    public ReservationResponse createReservation(ReservationRequest reservationRequest) {

        if(Objects.isNull(reservationRequest.getStartDate())) {
            throw new SeseException(SeseError.RESERVATION_NO_START_DATE);
        }
        if(Objects.isNull(reservationRequest.getEndDate())) {
            throw new SeseException(SeseError.RESERVATION_NO_END_DATE);
        }
        if(CollectionUtils.isEmpty(reservationRequest.getRoomReservations())) {
            throw new SeseException(SeseError.RESERVATION_NO_ROOMS);
        }
        List<RoomReservation> roomReservations = new ArrayList<>();
        for(RoomReservationRequest roomReservationRequest : reservationRequest.getRoomReservations()) {
            Optional<Room> optionalRoom = roomRepository.findById(roomReservationRequest.getRoomId());
            if(optionalRoom.isPresent()) {
                Room room = optionalRoom.get();
                RoomReservation roomReservation = new RoomReservation();
                roomReservation.setPension(roomReservationRequest.getPension());
                roomReservation.setRoom(room);
                roomReservation.setAdults(roomReservationRequest.getAdults());
                roomReservation.setChildren(roomReservationRequest.getChildren());
                roomReservations.add(roomReservation);
            } else {
                throw new SeseException(SeseError.RESERVATION_INVALID_ROOM);
            }
        }
        Customer customer;
        if(Objects.nonNull(reservationRequest.getCustomerId())) {
            Optional<Customer> customerOptional = customerRepository.findById(reservationRequest.getCustomerId());

            if(customerOptional.isPresent()) {
                customer = customerOptional.get();
            } else {
                throw new SeseException(SeseError.RESERVATION_INVALID_CUSTOMER);
            }
        } else if(Objects.nonNull(reservationRequest.getCustomer())) {
            customer = modelMapper.map(reservationRequest.getCustomer(), Customer.class);
            customerService.addNewCustomer(customer);
        } else {
            throw new SeseException(SeseError.RESERVATION_NO_CUSTOMER);
        }

        Reservation reservation = new Reservation();
        reservation.setStartDate(reservationRequest.getStartDate());
        reservation.setEndDate(reservationRequest.getEndDate());
        reservation.setCustomer(customer);
        reservation.setRoomReservations(roomReservations);
        roomReservations.forEach(roomReservation -> roomReservation.setReservation(reservation));

        sendReservationMail(reservation);

        Reservation saved = reservationRepository.save(reservation);

        logService.logAction("Eine neue Reservierung mit der Id '" + saved.getId() + "' wurde erstellt.");

        return new ReservationResponse(saved);
    }

    private void sendReservationMail(Reservation reservation) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", reservation.getCustomer().getName());
        variables.put("startDate", reservation.getStartDate().toLocalDate());
        variables.put("endDate", reservation.getEndDate().toLocalDate());
        String htmlText = TemplateUtil.processTemplate("reservierungs_mail", variables);
        byte[] pdfAttachment = PdfGenerationUtil.createPdf("reservierungs_pdf", variables);
        mailService.sendMailWithAttachment("hotelverwaltung@sese.at", reservation.getCustomer().getEmail(), "Ihre Reservierung ist abgeschlossen", htmlText , "bestaetigung.pdf", pdfAttachment, "application/pdf");
    }

    public ReservationResponse getReservationById(Long reservationId)
    {
       Optional<Reservation> reservationOptional=reservationRepository.findById(reservationId);
       Reservation reservation;

       if(reservationOptional.isPresent())
           reservation=reservationOptional.get();

       else
           throw new SeseException(SeseError.RESERVATION_NOT_FOUND);

       return new ReservationResponse(reservation);
    }

    public List<ReservationResponse> getReservationByKeyword(String keyword)
    {
        List<Reservation> reservationList=reservationRepository.findByCustomerNameContaining(keyword);
        List<ReservationResponse> reservationResponseList=new ArrayList<>();

        for(Reservation reservation:reservationList)
        {
            reservationResponseList.add(new ReservationResponse(reservation));
        }

        return reservationResponseList;
    }

    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll().stream().map(ReservationResponse::new).collect(Collectors.toList());
    }

    public List<ReservationResponse> getTodaysStartingReservations()
    {
        List<Reservation>reservationList=reservationRepository.findByStartDateBetween(getStartOfToday(),getEndOfToday());
        List<ReservationResponse> reservationResponseList=new ArrayList<>();

        for(Reservation reservation: reservationList)
        {
            reservationResponseList.add(new ReservationResponse(reservation));
        }

        return reservationResponseList;
    }

    private OffsetDateTime getStartOfToday()
    {
        OffsetDateTime now=OffsetDateTime.now();

        String monat;
        String tag;

        if(now.getMonthValue()<10)
            monat="0"+now.getMonthValue();
        else
            monat=""+now.getMonthValue();

        if(now.getDayOfMonth()<10)
            tag="0"+now.getDayOfMonth();
        else
            tag=""+now.getDayOfMonth();

        OffsetDateTime startOfToday=OffsetDateTime.parse(now.getYear()+"-"+monat+"-"+tag+"T00:00:00+01:00");
        return startOfToday;
    }

    private OffsetDateTime getEndOfToday()
    {
        OffsetDateTime endOfToday=getStartOfToday().plusDays(1);
        return endOfToday;
    }

    public List<ReservationResponse> getTodaysEndingReservations()
    {
        List<Reservation>reservationList=reservationRepository.findByEndDateBetween(getStartOfToday(),getEndOfToday());
        List<ReservationResponse> reservationResponseList=new ArrayList<>();

        for(Reservation reservation: reservationList)
        {
            reservationResponseList.add(new ReservationResponse(reservation));
        }

        return reservationResponseList;
    }
}
