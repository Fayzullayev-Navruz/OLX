package uz.pdp.securitytest.entity;



import jakarta.persistence.*;
import jdk.jfr.Enabled;

@Entity
public class UserMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String message;
    @ManyToOne(cascade = CascadeType.ALL)
    private User sender;
    @ManyToOne(cascade = CascadeType.ALL)
    private User accepter;

    public UserMessage(String message, User sender, User accepter) {
        this.message = message;
        this.sender = sender;
        this.accepter = accepter;
    }

    public UserMessage() {
    }

    public UserMessage(Integer id, String message, User sender, User accepter) {
        this.id = id;
        this.message = message;
        this.sender = sender;
        this.accepter = accepter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getAccepter() {
        return accepter;
    }

    public void setAccepter(User accepter) {
        this.accepter = accepter;
    }
}

