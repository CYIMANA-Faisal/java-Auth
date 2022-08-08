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
    
    public Admin(String email, String password, String firstName, String lastName, Integer age, Gender gender) {
        super(email, password, firstName, lastName, age, gender, Role.ADMIN);
    }

    

    @Override
    public User createUser(User user) {
        UserRepository.registerUser(user);
        return user;
    }
}
