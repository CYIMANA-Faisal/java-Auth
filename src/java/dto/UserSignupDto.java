/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import model.Gender;
import model.Role;

/**
 *
 * @author cyimana
 */
@XmlRootElement
public class UserSignupDto {

    @Override
    public String toString() {
        return "UserSignupDto{" + "firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber + ", email=" + email + ", age=" + age + ", gender=" + gender + ", role=" + role + '}';
    }
    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String email;
    public String password;
    public Integer age;
    public Gender gender;
    @NotNull
    @Enumerated(EnumType.STRING)
    public Role role;
}
