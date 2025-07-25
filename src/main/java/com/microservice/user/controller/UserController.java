package com.microservice.user.controller;

import com.microservice.user.RabbitMQConfig;
import com.microservice.user.entity.ResponseDto;
import com.microservice.user.entity.User;
import com.microservice.user.entity.UserDto;
import com.microservice.user.services.UserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostMapping
    ResponseEntity<User> saveUser(@RequestBody User user) {

        User saveDepartment = userService.saveUser(user);
        System.out.println("User Info"+saveDepartment.getEmail());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE,RabbitMQConfig.ROUTING_KEY,saveDepartment.getDepartmentId());
        return new ResponseEntity<>(saveDepartment, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseDto> getUser(@PathVariable("id") Long userId){
        ResponseDto responseDto = userService.getUser(userId);
        return ResponseEntity.ok(responseDto);
    }
}
