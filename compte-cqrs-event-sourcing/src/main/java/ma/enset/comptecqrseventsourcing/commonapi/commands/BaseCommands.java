package ma.enset.comptecqrseventsourcing.commonapi.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public abstract class BaseCommands<T> {
    @TargetAggregateIdentifier
    @Getter
    private T id;

    public BaseCommands(T id) {
        this.id = id;
    }
}
