package ma.enset.comptecqrseventsourcing.commonapi.events;

import lombok.Getter;
import ma.enset.comptecqrseventsourcing.commonapi.enums.AcountStatus;

@Getter
public class AccountActivatedEvent extends BaseEvents<String>{
    private AcountStatus status;

    public AccountActivatedEvent(String id, AcountStatus status) {
        super(id);
        this.status = status;
    }
}
