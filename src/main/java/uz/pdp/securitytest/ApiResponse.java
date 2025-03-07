package uz.pdp.securitytest;

public class ApiResponse {
    private String status;
    private String message;

    public ApiResponse(String message) {
        this.status = "success";
        this.message = message;
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

