package by.tms.diploma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ClientRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Enter something...")
    private String name;
    @Pattern(regexp = "(\\+)?\\d{7,15}")
    private String number;
    @ManyToMany
    private List<Tour> tours;
    private ClientRequestStatusEnum requestStatus;
    private long curatorId;
}
