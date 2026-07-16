package com.ZestAssignment.EmployeeManagment.Exception;

public class TokenExpired extends EmployeeException {
    public TokenExpired() {
    }

    public TokenExpired(String message) {
        super(message);
    }

    public TokenExpired(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenExpired(Throwable cause) {
        super(cause);
    }

    public TokenExpired(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
