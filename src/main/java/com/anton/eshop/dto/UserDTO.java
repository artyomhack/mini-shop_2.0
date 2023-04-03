package com.anton.eshop.dto;

import com.anton.eshop.data.Role;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private Long id;

    @NotBlank(message = "Name is required")
    private String username;

    @Length(min = 4, max = 16, message = "Password must be greater than 4 and no more than 16")
    private String password;


    @Length(min = 4, max = 16, message = "Password must be greater than 4 and no more than 16")
    private String matchingPassword;

    @Email
    private String email;

    @NotBlank(message = "Number is required")
    private String number;

    private Role role = Role.CLIENT;
}
