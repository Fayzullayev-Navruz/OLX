package uz.pdp.securitytest.payload;



public class UserMessageDto {
    private Integer Id;
    private String message;
    private String senderName;
    private Integer productId;

    public UserMessageDto(String sender, String message) {
        this.senderName = sender;
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

    public String getSender() {
        return senderName;
    }

    public void setSender(String sender) {
        this.senderName = sender;
    }



    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}

