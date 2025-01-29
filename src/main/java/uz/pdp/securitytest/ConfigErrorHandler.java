package uz.pdp.securitytest;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@ControllerAdvice
public class ConfigErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView defaultHandler(MethodArgumentNotValidException e) throws Exception {
        ModelAndView mav = new ModelAndView("error");
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            mav.addObject(fieldError.getField(), fieldError.getDefaultMessage());
        }


        return mav;
    }
}
