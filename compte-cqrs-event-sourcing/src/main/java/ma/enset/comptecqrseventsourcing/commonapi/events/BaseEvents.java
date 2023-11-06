package ma.enset.comptecqrseventsourcing.commonapi.events;

import lombok.Getter;

@Getter
public abstract class BaseEvents<T> {
    private T id;

    public BaseEvents(T id) {
        this.id = id;
    }
}
