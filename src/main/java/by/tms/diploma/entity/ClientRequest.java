package by.tms.diploma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest {
    private long id;
    @NotBlank(message = "Enter something...")
    private String name;
    @Pattern(regexp = "(\\+)?\\d{7,15}")
    private String number;
    private List<Tour> tours;
    private ClientRequestProgressEnum progress;
    private long curatorId;
}
