package by.tms.diploma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserChangePasswordModel {
    @NotBlank
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    private String secretSentence;
    @Pattern(regexp = "(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{8,16}$",
            message = "Must contain upper and lower case, digits. Min size - 8, max - 16!")
    private String password;
    private String confirmPassword;
}
