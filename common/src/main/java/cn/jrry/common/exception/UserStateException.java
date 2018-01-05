package cn.jrry.common.exception;

public class UserStateException extends RuntimeException {
    public UserStateException() {
    }

    public UserStateException(String message) {
        super(message);
    }
}
