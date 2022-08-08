/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.HashMap;
import java.util.Map;
import model.User;

/**
 *
 * @author cyimana
 */
public class UserRepository {
    
    private static Map<String,User> userDb=new HashMap<String,User>();

    public static void registerUser(User user){
         userDb.put(user.getEmail(),user);
    }
    
    public static User getUserByEmail(String email){
        return userDb.get(email);
    }
    
    public UserRepository() {
    }

    public Map<String, User> getUserDb() {
        return userDb;
    }

    public void setUserDb(Map<String, User> userDb) {
        this.userDb = userDb;
    }

    @Override
    public String toString() {
        return "UserRepository{" + "userDb=" + userDb + '}';
    }
   
    
}
