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
public class Pharmacist extends User {
    public Pharmacist() {
    }
    
    public Pharmacist(String email, String password, String firstName, String phoneNumber, String lastName, Integer age, Gender gender) {
        super(email, password, firstName, lastName, phoneNumber, age, gender, Role.PHARMACIST);
    }

    

    @Override
    public User createUser(User user) {
        UserRepository.registerUser(user);
        return user;
    }
}
