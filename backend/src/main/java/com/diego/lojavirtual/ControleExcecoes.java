package com.diego.lojavirtual;

import com.diego.lojavirtual.model.dto.ObjetoErroDto;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.List;

@RestControllerAdvice
@ControllerAdvice
public class ControleExcecoes extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ObjetoErroDto objetoErroDto = new ObjetoErroDto();

        String msg = "";

        if (ex instanceof MethodArgumentNotValidException) {
            List<ObjectError> list = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();

            for (ObjectError objectError : list) {
                msg += objectError.getDefaultMessage() + "\n";
            }
        } else {
            msg = ex.getMessage();
        }

        objetoErroDto.setMsgErro(msg);
        objetoErroDto.setCode(status.value() + " ==> " + status.getReasonPhrase());

        return new ResponseEntity<>(objetoErroDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class})
    protected ResponseEntity<Object> handleExceptionDataIntegrity(Exception ex) {

        ObjetoErroDto objetoErroDto = new ObjetoErroDto();

        String msg = "";

        if (ex instanceof ConstraintViolationException) {
            msg = ((ConstraintViolationException) ex).getCause().getCause().getMessage();
        } else
        if (ex instanceof DataIntegrityViolationException) {
            msg = ((DataIntegrityViolationException) ex).getCause().getCause().getMessage();
        } else
        if (ex instanceof SQLException) {
            msg = ((SQLException) ex).getCause().getCause().getMessage();
        } else {
            msg = ex.getMessage();
        }

        objetoErroDto.setMsgErro(msg);
        objetoErroDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());

        return new ResponseEntity<>(objetoErroDto, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
