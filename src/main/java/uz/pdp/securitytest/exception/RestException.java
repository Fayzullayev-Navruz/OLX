package uz.pdp.securitytest.exception;

public class RestException extends RuntimeException {


    public static RestException notFound(String message, Object id) {


        if (id == null)
            return new RestException(message);
        else
            return new RestException(message + "with id " + id);

    }

    public static RestException alreadyHave(String message, Object name) {


        if (name == null)
            return new RestException(message);
        else
            return new RestException(message + "with name " + name);

    }

    public RestException(String message) {
        super(message);
    }
}
