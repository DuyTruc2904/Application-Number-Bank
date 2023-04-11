package Exception;

public class CustomerIdNotValidException extends RuntimeException{
    public CustomerIdNotValidException(String messager) {
        super(messager);
    }
}
