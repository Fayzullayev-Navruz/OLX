package uz.pdp.securitytest;

import lombok.Data;

@Data
public class ApiResponse<E> {
    private String status;
    private E element;
    private String message;

    public ApiResponse(String message) {
        this.status = "success";
        this.message = message;
    }
    public ApiResponse(String status, E element) {
        this.status = status;
        this.element = element;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

