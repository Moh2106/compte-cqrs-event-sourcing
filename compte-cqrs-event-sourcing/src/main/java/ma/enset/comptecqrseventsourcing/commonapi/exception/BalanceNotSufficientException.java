package ma.enset.comptecqrseventsourcing.commonapi.exception;

public class BalanceNotSufficientException extends RuntimeException {
    public BalanceNotSufficientException(String s) {
        super(s);
    }
}
