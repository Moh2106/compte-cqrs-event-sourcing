package ma.enset.comptecqrseventsourcing.commonapi.queries;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAccountByIdQuery {
    private String id;

}
