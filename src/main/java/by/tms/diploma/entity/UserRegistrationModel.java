package by.tms.diploma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationModel {

//    @Pattern(regexp = "[A-Za-z_0-9-]{5,15}", message = "Login may have only upper and lower case, digits and '-', '_'. " +
//            "Min length - 5, max - 15!")
    private String username;
//    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[a-z]).{8,16}$",
//            message = "Password must have upper and lower case, digit. Min size - 8, max - 16!")
    private String password;
//    @Pattern(regexp = "\\S{3,10}@((gmail.com)|(mail.ru)|(yahoo.com)|(yandex.ru))",
//            message = "{length 3-10}@gmail.com/@mail.ru/@yahoo.com/@yandex.ru")
    private String email;
}
