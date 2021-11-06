package application.exeption;

public class MyException extends Exception{

    private final String message;

    public MyException(String msg) {
        this.message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
