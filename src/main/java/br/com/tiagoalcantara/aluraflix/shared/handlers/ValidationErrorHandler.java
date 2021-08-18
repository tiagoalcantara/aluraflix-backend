package br.com.tiagoalcantara.aluraflix.shared.handlers;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestControllerAdvice
public class ValidationErrorHandler {

    private final MessageSource messageSource;

    public ValidationErrorHandler(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorResponse handle(MethodArgumentNotValidException exception) {
        List<ObjectError> globalErrors = exception.getBindingResult()
                .getGlobalErrors();
        List<FieldError> fieldErrors = exception.getBindingResult()
                .getFieldErrors();

        return buildFieldErrorResponse(globalErrors, fieldErrors);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ValidationErrorResponse> handle(ResponseStatusException exception) {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();

        validationErrorResponse.addGlobalError(exception.getReason());

        return ResponseEntity.status(exception.getStatus()).body(validationErrorResponse);
    }

    private ValidationErrorResponse buildFieldErrorResponse(List<ObjectError> globalErrors,
                                                                 List<FieldError> fieldErrors) {
        ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();

        globalErrors.forEach(error -> validationErrorResponse.addGlobalError(getMessage(error)));

        fieldErrors.forEach(error -> {
            validationErrorResponse.addFieldError(error.getField(), getMessage(error));
        });

        return validationErrorResponse;
    }

    private String getMessage(ObjectError error) {
        return messageSource.getMessage(error, LocaleContextHolder.getLocale());
    }

}
