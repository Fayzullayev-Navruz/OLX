package uz.pdp.securitytest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.pdp.securitytest.ApiResponse;
import uz.pdp.securitytest.entity.Product;
import uz.pdp.securitytest.entity.User;
import uz.pdp.securitytest.entity.UserMessage;
import uz.pdp.securitytest.payload.SenderDto;
import uz.pdp.securitytest.payload.UserMessageDto;
import uz.pdp.securitytest.repository.ProductRepository;
import uz.pdp.securitytest.repository.UserMessageRepository;
import uz.pdp.securitytest.repository.UserRepository;
import uz.pdp.securitytest.service.UserMessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/message")
@CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true")
public class UserMessageController {
    @Autowired
    private final UserMessageRepository userMessageRepository;
    @Autowired
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final UserMessageService userMessageService;

    public UserMessageController(UserMessageRepository userMessageRepository, ProductRepository productRepository, UserRepository userRepository, UserMessageService userMessageService) {
        this.userMessageRepository = userMessageRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.userMessageService = userMessageService;
    }
//
//    @GetMapping
//    public List<UserMessageDto> getMessage() {
//        ArrayList<UserMessageDto> userMessageDtos = new ArrayList<>();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) auth.getPrincipal();
//        List<UserMessage> messages = userMessageRepository.findByUserId(user.getId());
//
//        for (UserMessage message : messages) {
//
//            UserMessageDto dto = new UserMessageDto();
//            dto.setMessage(message.getMessage());
//            dto.setSender(new SenderDto(message.getSender().getId(), message.getSender().getUsername()));
//            dto.setProductId(message.getProduct().getId());
//            userMessageDtos.add(dto);
//        }
//        return userMessageDtos;
//    }

    @PostMapping()
    public ApiResponse addMessage(@RequestBody UserMessageDto dto) {
        userMessageService.addMessage(dto);

       return new ApiResponse<>("muvaffaqilayatli qo'shildi");
    }

    @PostMapping("/{email}")
    public ApiResponse addMessage(@PathVariable String email, @RequestBody UserMessageDto dto) {

     userMessageService.addMessage(dto, email);
        return new ApiResponse("qo'shildi");
    }

    @GetMapping("/{userName}")
   ApiResponse getOwnMessages(@PathVariable String userName) {
        List<UserMessageDto> ownMessages = userMessageService.getOwnMessages(userName);
        return new ApiResponse<>(HttpStatus.OK.toString(), ownMessages);
    }

    @GetMapping("/chats")
    public ApiResponse getChats() {
        List<String> chats = userMessageService.getChats();
        return new ApiResponse<>(HttpStatus.OK.toString(), chats);
    }


}

