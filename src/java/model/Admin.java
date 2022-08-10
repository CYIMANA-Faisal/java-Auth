/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import repository.UserRepository;

/**
 *
 * @author cyimana
 */
public class Admin extends User {
    public Admin() {
    }
    
    public Admin(String email, String password, String firstName, String lastName, String phoneNumber, Integer age, Gender gender) {
        super(email, password, firstName, lastName, phoneNumber, age, gender, Role.ADMIN);
    }

    

    @Override
    public User create() {
        UserRepository.registerUser(this);
        return this;
    }
}
