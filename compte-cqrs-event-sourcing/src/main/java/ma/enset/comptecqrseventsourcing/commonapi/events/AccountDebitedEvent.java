package ma.enset.comptecqrseventsourcing.commonapi.events;

import lombok.Getter;

@Getter
public class AccountDebitedEvent extends BaseEvents<String>{
    private double amount;
    private String accuracy;

    public AccountDebitedEvent(String id, double amount, String accuracy) {
        super(id);
        this.amount = amount;
        this.accuracy = accuracy;
    }
}
