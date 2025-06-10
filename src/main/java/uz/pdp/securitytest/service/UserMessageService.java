package uz.pdp.securitytest.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
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

@Service
public class UserMessageService {
    private final UserMessageRepository userMessageRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public UserMessageService(UserMessageRepository userMessageRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.userMessageRepository = userMessageRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public void addMessage(UserMessageDto dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if (dto.getId() != null && dto.getId() != user.getId()) {
            throw new SecurityException("You are not allowed to add this message");
        }
        Product product = productRepository.findById(dto.getProductId()).get();

        Optional<User> byId = userRepository.findById(user.getId());
        UserMessage userMessage = new UserMessage();
        userMessage.setSender(byId.get());
        userMessage.setMessage(dto.getMessage());
        userMessage.setAccepter(product.getUser());
        userMessage.setProduct(product);
        userMessageRepository.save(userMessage);
    }

    public void addMessage(UserMessageDto dto, String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        User user1 = byEmail.get();
        Product product = productRepository.findById(dto.getProductId()).get();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Optional<User> byId = userRepository.findById(user.getId());
        UserMessage userMessage = new UserMessage();
        userMessage.setSender(byId.get());
        userMessage.setMessage(dto.getMessage());
        userMessage.setAccepter(user1);
        userMessage.setProduct(product);
        userMessageRepository.save(userMessage);
    }

    public List<UserMessageDto> getOwnMessages(String userName) {
        ArrayList<UserMessageDto> userMessageDtos = new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        List<UserMessage> messages = userMessageRepository.findByChat(user.getId(), userName);
        for (UserMessage message : messages) {
            UserMessageDto dto = new UserMessageDto();
            dto.setMessage(message.getMessage());
            dto.setSender(new SenderDto(message.getSender().getId(), message.getSender().getEmail()));
            dto.setProductId(message.getProduct().getId());
            userMessageDtos.add(dto);
        }
        return userMessageDtos;
    }

    public List<String> getChats() {
        ArrayList<String> userMessageDtos = new ArrayList<>();
        ArrayList<User> users = new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        // String fullName = user.getFullName();
        String email = user.getEmail();
        List<UserMessage> messages = userMessageRepository.findByUserId(user.getId());
        for (UserMessage message : messages) {
            if (!userMessageDtos.contains(email) && !email.equals(message.getAccepter().getEmail())) {
                userMessageDtos.add(message.getAccepter().getEmail());
            } else {
//                if (!userMessageDtos.contains(message.getAccepter().getEmail()) && !Objects.equals(email, message.getSender().getEmail())) {
//                    userMessageDtos.add(message.getAccepter().getEmail());
//                }
                if (!userMessageDtos.contains(message.getSender().getEmail()))
                    userMessageDtos.add(message.getSender().getEmail());
            }
        }
        return userMessageDtos;
    }
}
