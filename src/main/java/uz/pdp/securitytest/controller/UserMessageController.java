package uz.pdp.securitytest.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import java.util.ArrayList;
import java.util.List;
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

    public UserMessageController(UserMessageRepository userMessageRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.userMessageRepository = userMessageRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<UserMessageDto> getMessage() {
        ArrayList<UserMessageDto> userMessageDtos = new ArrayList<>();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//     User user = (User)auth.getPrincipal();
        List<UserMessage> messages = userMessageRepository.findByUserId(1);

        for (UserMessage message : messages) {

            UserMessageDto dto = new UserMessageDto();
            dto.setMessage(message.getMessage());
            dto.setSender(new SenderDto(message.getSender().getId(), message.getSender().getUsername()));
            dto.setProductId(message.getProduct().getId());
            userMessageDtos.add(dto);
        }
        return userMessageDtos;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse>  addMessage(@RequestBody UserMessageDto dto) {


        Product product = productRepository.findById(dto.getProductId()).get();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) auth.getPrincipal();
        Optional<User> byId = userRepository.findById(2);
        UserMessage userMessage = new UserMessage();
        userMessage.setSender(byId.get());
        userMessage.setMessage(dto.getMessage());
        userMessage.setAccepter(product.getUser());
        userMessage.setProduct(product);
        userMessageRepository.save(userMessage);
        return ResponseEntity.ok(new ApiResponse("qo'shildi"));
    }

    @GetMapping("/{userName}")
    List<UserMessageDto> getOwnMessages(@PathVariable String userName) {
        ArrayList<UserMessageDto> userMessageDtos = new ArrayList<>();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User)auth.getPrincipal();
        List<UserMessage> messages = userMessageRepository.findByChat(1, userName);
        for (UserMessage message : messages) {
            UserMessageDto dto = new UserMessageDto();
            dto.setMessage(message.getMessage());
            dto.setSender(new SenderDto(message.getSender().getId(), message.getSender().getUsername()));
            dto.setProductId(message.getProduct().getId());
            userMessageDtos.add(dto);
        }
        return userMessageDtos;
    }

    @GetMapping("/chats")
    public List<String> getChats() {
        ArrayList<String> userMessageDtos = new ArrayList<>();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//     User user = (User)auth.getPrincipal();
        List<UserMessage> messages = userMessageRepository.findByUserId(1);
        for (UserMessage message : messages) {
            if (message.getSender().getId() != 1 && !userMessageDtos.contains(message.getSender().getUsername()))
                userMessageDtos.add(message.getSender().getUsername());
            else {
                if (!userMessageDtos.contains(message.getAccepter().getUsername())) {
                    userMessageDtos.add(message.getAccepter().getUsername());
                }
            }
        }
        return userMessageDtos;
    }


}

