/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import model.Gender;
import model.Role;

/**
 *
 * @author cyimana
 */
public class UserSignupDto {
    public String firstName;
    public String lastName;
    public String phoneNumber;
    public String email;
    public String password;
    public Integer age;
    public Gender gender;
    public Role role;
}
