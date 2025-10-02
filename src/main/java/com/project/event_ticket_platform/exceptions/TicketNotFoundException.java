package com.project.event_ticket_platform.exceptions;

public class TicketNotFoundException extends RuntimeException {

    protected TicketNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public TicketNotFoundException(Throwable cause) {
        super(cause);
    }

    public TicketNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketNotFoundException() {
        super();
    }

    public TicketNotFoundException(String message) {
        super(message);
    }
}
