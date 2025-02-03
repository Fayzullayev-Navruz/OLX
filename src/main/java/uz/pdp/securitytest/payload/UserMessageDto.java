package uz.pdp.securitytest.payload;



public class UserMessageDto {
    private Integer Id;
    private String message;
    private SenderDto sender;

    private Integer productId;

    public UserMessageDto(SenderDto sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public UserMessageDto() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public SenderDto getSender() {
        return sender;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setSender(SenderDto sender) {
        this.sender = sender;
    }
}

