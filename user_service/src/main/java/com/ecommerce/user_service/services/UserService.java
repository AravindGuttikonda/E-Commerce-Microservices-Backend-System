package com.ecommerce.user_service.services;

import com.ecommerce.user_service.dtos.EmailContent;
import com.ecommerce.user_service.dtos.UserRegisterDto;
import com.ecommerce.user_service.exceptions.UserNotFoundException;
import com.ecommerce.user_service.models.UserData;
import com.ecommerce.user_service.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public UserData addUser(UserRegisterDto userRegisterDto) throws JsonProcessingException {
        String name = userRegisterDto.getName();
        String email = userRegisterDto.getEmail();
        String mobile = userRegisterDto.getMobile();
        UserData userData = new UserData();
        userData.setName(name);
        userData.setEmail(email);
        userData.setMobile(mobile);
        UserData savedUser = userRepository.save(userData);
        String subject = "User added successfully!";
        String body = "User with id " + savedUser.getId() + " is added successfully";
        EmailContent emailContent = new EmailContent("guttikondaaravind39@gmail.com", userRegisterDto.getEmail(), subject, body);
        ObjectMapper objectMapper = new ObjectMapper();
        String valueAsString = objectMapper.writeValueAsString(emailContent);
        kafkaTemplate.send("email-topic", valueAsString);
        return savedUser;
    }
    public UserData getUser(Long id) throws UserNotFoundException {
        Optional<UserData> byId = userRepository.findById(id);
        if(byId.isEmpty()){
            throw new UserNotFoundException();
        }
        return byId.get();
    }
}
