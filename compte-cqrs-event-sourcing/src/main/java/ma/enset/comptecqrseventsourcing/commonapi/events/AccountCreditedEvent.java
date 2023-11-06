package ma.enset.comptecqrseventsourcing.commonapi.events;

import lombok.Getter;

@Getter
public class AccountCreditedEvent extends BaseEvents<String>{
    private double amount;
    private String accuracy;

    public AccountCreditedEvent(String id, double amount, String accuracy) {
        super(id);
        this.amount = amount;
        this.accuracy = accuracy;
    }
}
