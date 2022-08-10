/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import org.mindrot.bcrypt.BCrypt;
import repository.UserRepository;

/**
 *
 * @author cyimana
 */
public class Patient extends User{

    public Patient() {
    }
    
    public Patient(String email, String password, String firstName, String lastName, String phoneNumber, Integer age, Gender gender) {
        super(email, password, firstName, lastName, phoneNumber, age, gender, Role.PATIENT);
    }

    

    @Override
    public User create() {
        UserRepository.registerUser(this);
        return this;
    }
 
}
