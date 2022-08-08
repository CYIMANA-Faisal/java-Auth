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
public class Physician extends User {
    public Physician() {
    }
    
    public Physician(String email, String password, String firstName, String lastName, Integer age, Gender gender) {
        super(email, password, firstName, lastName, age, gender, Role.PHYSICIAN);
    }

    

    @Override
    public User createUser(User user) {
        UserRepository.registerUser(user);
        return user;
    }
}
