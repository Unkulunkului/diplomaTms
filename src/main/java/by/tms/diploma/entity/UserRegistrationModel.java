package by.tms.diploma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationModel {

    @Pattern(regexp = "[A-Za-z_0-9-]{5,15}", message = "Only upper and lower case, digits and '-', '_'. " +
            "Min length - 5, max - 15!")
    private String username;
    @Pattern(regexp = "(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{8,16}$",
            message = "Must contain upper and lower case, digits. Min size - 8, max - 16!")
    private String password;
    private String confirmPassword;
    @Pattern(regexp = "\\S{3,15}@((gmail.com)|(mail.ru)|(yahoo.com)|(yandex.ru))",
            message = "[length 3-15]@gmail.com, mail.ru, @yahoo.com, @yandex.ru")
    private String email;
    @Pattern(regexp = "[A-Za-z_0-9-\\s\\.\\,]{5,15}", message = "Only upper and lower case, digits and '-' '_' ',' '.' ' '" +
            "Min length - 5, max - 15!")
    private String secretSentence;
}
