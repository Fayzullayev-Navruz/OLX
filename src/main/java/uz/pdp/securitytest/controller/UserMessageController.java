package uz.pdp.securitytest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.pdp.securitytest.entity.Product;
import uz.pdp.securitytest.entity.User;
import uz.pdp.securitytest.entity.UserMessage;
import uz.pdp.securitytest.payload.UserMessageDto;
import uz.pdp.securitytest.repository.ProductRepository;
import uz.pdp.securitytest.repository.UserMessageRepository;
import uz.pdp.securitytest.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/message")
public class UserMessageController {
    @Autowired
    private final UserMessageRepository userMessageRepository;
    @Autowired
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public UserMessageController(UserMessageRepository userMessageRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.userMessageRepository = userMessageRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<UserMessageDto> getMessage() {
        ArrayList<UserMessageDto> userMessageDtos = new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
     User user = (User)auth.getPrincipal();
          List<UserMessage> messages = userMessageRepository.findByUserId(user.getId());

        for (UserMessage message : messages) {

            UserMessageDto dto = new UserMessageDto();
            dto.setMessage(message.getMessage());
            dto.setSender(message.getSender().getUsername());
            userMessageDtos.add(dto);
        }
        return userMessageDtos;
    }

    @PostMapping
    public void addMessage(@RequestBody UserMessageDto dto) {
        Product product = productRepository.findById(dto.getProductId()).get();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)auth.getPrincipal();
        Optional<User> byId = userRepository.findById(2);
        UserMessage userMessage = new UserMessage();
        userMessage.setSender(byId.get());
        userMessage.setMessage(dto.getMessage());
        userMessage.setAccepter(product.getUser());
        userMessageRepository.save(userMessage);
    }

    @GetMapping("/own")
    List<UserMessageDto> getOwnMessages() {
        ArrayList<UserMessageDto> userMessageDtos = new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)auth.getPrincipal();
        List<UserMessage> messages = userMessageRepository.findBySenderId(user.getId());
        for (UserMessage message : messages) {
            UserMessageDto dto = new UserMessageDto();
            dto.setMessage(message.getMessage());
            dto.setSender(message.getSender().getUsername());
            userMessageDtos.add(dto);
        }
        return userMessageDtos;
    }

}

