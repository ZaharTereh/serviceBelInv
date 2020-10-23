package by.compit.tereh.service.weblogic.exception;

public class WebLogicException extends Exception {

    public WebLogicException() {
    }

    public WebLogicException(String message) {
        super(message);
    }

    public WebLogicException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebLogicException(Throwable cause) {
        super(cause);
    }

    public WebLogicException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
