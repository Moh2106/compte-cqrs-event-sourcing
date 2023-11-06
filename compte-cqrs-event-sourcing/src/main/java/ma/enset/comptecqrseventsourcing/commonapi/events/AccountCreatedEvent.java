package ma.enset.comptecqrseventsourcing.commonapi.events;

import lombok.Getter;
import ma.enset.comptecqrseventsourcing.commonapi.enums.AcountStatus;

@Getter
public class AccountCreatedEvent extends BaseEvents<String>{
    private  double initialBalance;
    private String currency;
    private AcountStatus status;

    public AccountCreatedEvent(String id, double initialBalance, String currency, AcountStatus status) {
        super(id);
        this.initialBalance = initialBalance;
        this.currency = currency;
        this.status = status;
    }
}
