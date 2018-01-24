package cn.jrry.common.exception;

public class WxInvokeException extends RuntimeException {
    public WxInvokeException() {
    }

    public WxInvokeException(String message) {
        super(message);
    }

    public WxInvokeException(String message, Throwable cause) {
        super(message, cause);
    }

    public WxInvokeException(Throwable cause) {
        super(cause);
    }
}
