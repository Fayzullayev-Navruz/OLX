package uz.pdp.securitytest.entity;



import jakarta.persistence.*;
import jdk.jfr.Enabled;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

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
    @CreationTimestamp
    private Timestamp timestamp;

    @UpdateTimestamp
    private Timestamp updateTimestamp;

    @ManyToOne
    private Product product;

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

    public UserMessage(String message, User sender, User accepter, Product product) {
        this.message = message;
        this.sender = sender;
        this.accepter = accepter;
        this.product = product;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

