package ma.enset.comptecqrseventsourcing.query.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.comptecqrseventsourcing.commonapi.enums.AcountStatus;

import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    private String id;
    private double balance;
    @Enumerated(EnumType.STRING)
    private AcountStatus status;
    @OneToMany(mappedBy = "account")
    private Collection<Operation> operations;
}
