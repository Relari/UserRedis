package com.example.demo.controller;

import com.example.demo.business.model.User;
import com.example.demo.business.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "/rest/users")
@AllArgsConstructor
public class UserController {

    private UserRepository userRepository;

    @GetMapping(path = "/add/{id}/{name}")
    public User add(@PathVariable("id") final String id, @PathVariable("name") final String name){
        userRepository.save(new User(id, name, 200.00));

        log.info("Save in Redis id " + id);
        for (int i = 0; i <= 60; i++) {
            log.info("Count " + i + " id = " + id);
            delaySegundo();
            if (i== 60) {
                userRepository.delete(id);
                log.info("Delete id = " + id);
            }
        }

        return userRepository.findById(id);
    }

    private static void delaySegundo(){
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){}
    }

    @GetMapping(path = "/upd/{id}/{name}")
    public User update(@PathVariable("id") final String id, @PathVariable("name") final String name){
        userRepository.update(new User(id, name, 100.00));
        return userRepository.findById(id);
    }

    @GetMapping(path = "/{id}")
    public User find(@PathVariable("id") final String id){
        return userRepository.findById(id);
    }

    @GetMapping(path = "")
    public Map<String, User> all(){
        return userRepository.findAll();
    }
}
