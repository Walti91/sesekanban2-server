package sese.exceptions;

import org.springframework.http.HttpStatus;

public enum SeseError {

    /*
    Room errors 1000 - 1999
     */

    NO_ROOMS(HttpStatus.NOT_FOUND, 1000, "No rooms found!"),
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND,1002,"Room not found!"),

    /*
    Customer errors 2000 - 2999
     */

    NO_CUSTOMER(HttpStatus.NOT_FOUND, 2000, "No customer found!"),

    /*
    Reservation errors 3000 - 3999
     */

    RESERVATION_NO_START_DATE(HttpStatus.BAD_REQUEST, 3000, "No start date for reservation defined!"),
    RESERVATION_NO_END_DATE(HttpStatus.BAD_REQUEST, 3001, "No end date for reservation defined!"),
    RESERVATION_NO_CUSTOMER(HttpStatus.BAD_REQUEST, 3002, "No customer for reservation defined!"),
    RESERVATION_NO_ROOMS(HttpStatus.BAD_REQUEST, 3003, "No rooms for reservation defined!"),
    RESERVATION_INVALID_CUSTOMER(HttpStatus.BAD_REQUEST, 3004, "Invalid customer id defined for reservation!"),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND,3005,"Reservation not found!"),
    RESERVATION_INVALID_ROOM(HttpStatus.BAD_REQUEST, 3006, "Invalid room id for reservation!"),

    /*
    BILL ERRORS 4000 - 4999
     */

    BILL_ID_NOT_FOUND(HttpStatus.NOT_FOUND, 4000, "No bill with the delivered bill-id exists"),
    BILL_INVALID_RESERVATION(HttpStatus.NOT_FOUND, 4001, "No reservation with the delivered reservation-id from the bill exists"),

    /*
    PAYMENT ERRORS 5000 - 5999
     */

    PAYMENT_VALUE_SMALLER_ZERO(HttpStatus.BAD_REQUEST, 5000, "The amount of money must not be smaller than zero"),
    PAYMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, 5001, "No payment found for given payment id"),

    /*
    COMMENT ERRORS 6000-6999
     */

    COMMENT_TEXT_NULL_OR_EMPTY(HttpStatus.BAD_REQUEST, 6000, "The text of the comment must not be null or empty"),

    /*
    UTILS 7000-7999
     */
    PDF_GENERATION_ERROR(HttpStatus.BAD_REQUEST, 7000, "PDF could not be generated")
    ;

    private HttpStatus httpStatus;

    private Integer code;

    private String message;

    SeseError(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
