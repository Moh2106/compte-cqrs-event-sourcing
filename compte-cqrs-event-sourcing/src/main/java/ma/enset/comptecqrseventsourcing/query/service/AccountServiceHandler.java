package ma.enset.comptecqrseventsourcing.query.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.comptecqrseventsourcing.commonapi.enums.OperationType;
import ma.enset.comptecqrseventsourcing.commonapi.events.AccountActivatedEvent;
import ma.enset.comptecqrseventsourcing.commonapi.events.AccountCreatedEvent;
import ma.enset.comptecqrseventsourcing.commonapi.events.AccountCreditedEvent;
import ma.enset.comptecqrseventsourcing.commonapi.events.AccountDebitedEvent;
import ma.enset.comptecqrseventsourcing.commonapi.queries.GetAccountByIdQuery;
import ma.enset.comptecqrseventsourcing.commonapi.queries.GetAllAccountQuery;
import ma.enset.comptecqrseventsourcing.query.entities.Account;
import ma.enset.comptecqrseventsourcing.query.entities.Operation;
import ma.enset.comptecqrseventsourcing.query.repository.AccountRepository;
import ma.enset.comptecqrseventsourcing.query.repository.OperationRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class AccountServiceHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    @EventHandler
    public void on(AccountCreatedEvent event){
        log.info("**********************");
        log.info("AccountCreatedEvent received ");
        Account account = new Account();
        account.setId(event.getId());
        account.setBalance(event.getInitialBalance());
        account.setStatus(event.getStatus());
        account.setOperations(null);
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountActivatedEvent event){
        log.info("**********************");
        log.info("AccountActivatedEvent received ");
        Account account = accountRepository.findById(event.getId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountDebitedEvent event){
        log.info("**********************");
        log.info("AccountDebitedEvent received ");
        Account account = accountRepository.findById(event.getId()).get();

        Operation operation = new Operation();
        operation.setAmount(event.getAmount());
        operation.setDate(new Date());
        operation.setType(OperationType.DEBIT);
        operation.setAccount(account);
        operationRepository.save(operation);

        account.setBalance(account.getBalance() - event.getAmount());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountCreditedEvent event){
        log.info("**********************");
        log.info("AccountCreditedEvent received ");
        Account account = accountRepository.findById(event.getId()).get();

        Operation operation = new Operation();
        operation.setAmount(event.getAmount());
        operation.setDate(new Date());
        operation.setType(OperationType.CREDIT);
        operation.setAccount(account);
        operationRepository.save(operation);

        account.setBalance(account.getBalance() + event.getAmount());
        accountRepository.save(account);
    }

    @QueryHandler
    public List<Account> on(GetAllAccountQuery query){
        return accountRepository.findAll();
    }

    @QueryHandler
    public Account on(GetAccountByIdQuery query){
        return accountRepository.findById(query.getId()).get();
    }
}
