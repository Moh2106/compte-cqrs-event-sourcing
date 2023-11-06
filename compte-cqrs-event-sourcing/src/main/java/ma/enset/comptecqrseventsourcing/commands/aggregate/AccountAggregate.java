package ma.enset.comptecqrseventsourcing.commands.aggregate;

import ma.enset.comptecqrseventsourcing.commonapi.commands.CreateAccountCommand;
import ma.enset.comptecqrseventsourcing.commonapi.commands.CreditAccountCommand;
import ma.enset.comptecqrseventsourcing.commonapi.commands.DebitAccountCommand;
import ma.enset.comptecqrseventsourcing.commonapi.enums.AcountStatus;
import ma.enset.comptecqrseventsourcing.commonapi.events.AccountActivatedEvent;
import ma.enset.comptecqrseventsourcing.commonapi.events.AccountCreatedEvent;
import ma.enset.comptecqrseventsourcing.commonapi.events.AccountCreditedEvent;
import ma.enset.comptecqrseventsourcing.commonapi.events.AccountDebitedEvent;
import ma.enset.comptecqrseventsourcing.commonapi.exception.AmountNegativeException;
import ma.enset.comptecqrseventsourcing.commonapi.exception.BalanceNotSufficientException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AcountStatus status;

    public AccountAggregate() {
        // Required by AXON
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        if (createAccountCommand.getInitialBalance() < 0) throw new RuntimeException("Impossible de créer un compte avec un montant négatif");
        // Si tout marche bien
        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCommand.getId(),
                createAccountCommand.getInitialBalance(),
                createAccountCommand.getCurrency(),
                AcountStatus.CREATED
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        this.accountId = event.getId();
        this.balance = event.getInitialBalance();
        this.status = AcountStatus.CREATED;
        this.currency = event.getCurrency();
        AggregateLifecycle.apply(new AccountActivatedEvent(
                event.getId(),
                AcountStatus.ACTIVATED
        ));
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent event){
        this.status = event.getStatus();
    }

    @CommandHandler
    public void handle(CreditAccountCommand creditAccountCommand) throws AmountNegativeException {
        if (creditAccountCommand.getAmount() < 0) throw new AmountNegativeException("Amount Should not negative");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                creditAccountCommand.getId(),
                creditAccountCommand.getAmount(),
                creditAccountCommand.getCurrency()
        ));

    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event){
        this.balance += event.getAmount();
    }

    @CommandHandler
    public void handle(DebitAccountCommand debitAccountCommand) throws AmountNegativeException {
        if (debitAccountCommand.getAmount() < 0) throw new AmountNegativeException("Amount Should not negative");

        if (this.balance < debitAccountCommand.getAmount()) throw new BalanceNotSufficientException("Balance not sufficient exception "+balance);

        AggregateLifecycle.apply(new AccountDebitedEvent(
                debitAccountCommand.getId(),
                debitAccountCommand.getAmount(),
                debitAccountCommand.getCurrency()
        ));

    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent event){
        this.balance -= event.getAmount();
    }


}
