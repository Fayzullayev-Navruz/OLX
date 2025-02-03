package uz.pdp.securitytest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.pdp.securitytest.payload.ErrorDTO;

import java.sql.Timestamp;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorDTO> handleException(Exception e) {

        ErrorDTO errorDTO = new ErrorDTO(
                new Timestamp(System.currentTimeMillis()),
                e.getMessage(),
                500
        );
        System.out.println(e.getMessage());


        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(value = RestException.class)
    public ResponseEntity<ErrorDTO> handleRestException(RestException e) {
        ErrorDTO errorDTO = new ErrorDTO(
                new Timestamp(System.currentTimeMillis()),
                e.getMessage(),
                400
        );

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }



}
