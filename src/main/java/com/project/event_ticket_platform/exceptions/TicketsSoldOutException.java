package com.project.event_ticket_platform.exceptions;

public class TicketsSoldOutException extends RuntimeException {
    public TicketsSoldOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketsSoldOutException(Throwable cause) {
        super(cause);
    }

    protected TicketsSoldOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public TicketsSoldOutException(String message) {
        super(message);
    }
}
