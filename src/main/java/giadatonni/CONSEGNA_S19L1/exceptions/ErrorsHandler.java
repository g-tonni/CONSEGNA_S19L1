package giadatonni.CONSEGNA_S19L1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorsHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ErrorDTO handleBadRequest(BadRequestException ex){
        return new ErrorDTO(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorDTO handleBadRequest(NotFoundException ex){
        return new ErrorDTO(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ErrorDTO handleUnauthorized(UnauthorizedException ex){
        return new ErrorDTO(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ValidationException.class)
    public ErrorsDTO handleBadRequest(ValidationException ex){
        return new ErrorsDTO(ex.getMessage(), ex.listaErrori);
    }
}
