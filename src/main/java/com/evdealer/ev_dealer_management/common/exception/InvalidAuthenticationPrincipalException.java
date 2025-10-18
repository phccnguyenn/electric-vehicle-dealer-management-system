package com.evdealer.ev_dealer_management.common.exception;

import com.evdealer.ev_dealer_management.common.utils.MessagesUtils;

public class InvalidAuthenticationPrincipalException extends RuntimeException {
    private final String message;

    public InvalidAuthenticationPrincipalException(String errorCode, Object... var2) {
        this.message = MessagesUtils.getMessage(errorCode, var2);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
