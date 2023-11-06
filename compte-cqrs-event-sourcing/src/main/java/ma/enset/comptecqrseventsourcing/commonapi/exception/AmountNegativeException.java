package ma.enset.comptecqrseventsourcing.commonapi.exception;

public class AmountNegativeException extends RuntimeException {
    public AmountNegativeException(String message) {
        super(message);
    }
}
