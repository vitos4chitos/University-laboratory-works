package com.controllers;

import com.entities.AuthRequest;
import com.entities.User;
import com.entities.UserRepository;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.SaslServer;
import java.util.Iterator;

@RestController
public class GreetingController {
        @Autowired
        private UserRepository userRepository;

        @PostMapping(path="/auth")
        public @ResponseBody
        String entrance (@RequestBody AuthRequest auth) {

            User n = new User();
            n.setLogin(auth.getLogin());
            n.setPassword(BCrypt.hashpw(auth.getPassword().getBytes(), "$2a$10$7OQ0s5sdlHVx0Pp9AtnjUO"));
//            String pass = BCrypt.hashpw(password.getBytes(), "$2a$10$7OQ0s5sdlHVx0Pp9AtnjUO");
            System.out.println("Пизда");
//            userRepository.save(n);
            Iterable <User> users;
            try{
                users = userRepository.findAll();
            }
            catch (Exception e){
                return "{\"token\": \"" + "connection error" + "\"}";
            }
            for (User user : users) {
//                System.out.println(user.getLogin());
//                System.out.println(user.getPassword());
                if(user.getLogin().equals(auth.getLogin()) && user.getPassword().equals(n.getPassword())){
                    return "{\"token\": \"" + Integer.toString(user.getPassword().hashCode()) + "\"}";
                }
            }
//            System.out.println(auth.getLogin());
            return "{\"token\": \"" + "bad" + "\"}";
        }

    @PostMapping(path="/reg")
    public @ResponseBody
    String registration (@RequestBody AuthRequest auth) {
        System.out.println("Пизда");
        Iterable<User> users;
        try {
            users = userRepository.findAll();
        }
        catch (Exception e){
            return "{\"token\": \"" + "connection error" + "\"}";
        }
        for (User user : users) {
//            System.out.println(user.getLogin());
//            System.out.println(user.getPassword());
            if(user.getLogin().equals(auth.getLogin())){
                return "{\"token\": \"" + "bad" + "\"}";
            }
        }
        User n = new User();
        n.setLogin(auth.getLogin());
        n.setPassword(BCrypt.hashpw(auth.getPassword().getBytes(), "$2a$10$7OQ0s5sdlHVx0Pp9AtnjUO"));
        try {
            userRepository.save(n);
        }
        catch (Exception e){
            return "{\"token\": \"" + "connection error" + "\"}";
        }
        return "{\"token\": \"" + n.getPassword().hashCode() + "\"}";
    }

    @PostMapping(path="/checker")
    public String checkToken(@RequestBody AuthRequest user){
        Iterable <User> users;
        try{
            users = userRepository.findAll();
        }
        catch (Exception e){
            return "{\"token\": \"" + "connection error" + "\"}";
        }
        for (User user1 : users) {
//                System.out.println(user.getLogin());
//                System.out.println(user.getPassword());
            if(user.getLogin().equals(user1.getLogin())){
                return "{\"token\": \"" + user1.hash() + "\"}";
            }
        }
//            System.out.println(auth.getLogin());
        return "{\"token\": \"" + "bad" + "\"}";
    }
}

