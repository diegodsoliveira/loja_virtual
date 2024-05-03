package com.diego.lojavirtual.exceptions;

import com.diego.lojavirtual.model.dto.ObjetoErroDto;
import com.diego.lojavirtual.service.EmailService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

@RestControllerAdvice
@ControllerAdvice
public class ControleExcecoes extends ResponseEntityExceptionHandler {

    @Autowired private EmailService emailService;

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
        } else if (ex instanceof HttpMessageNotReadableException) {
            msg = "Corpo da requisição está vazio.";
        }else {
            msg = ex.getMessage();
        }

        objetoErroDto.setMsgErro(msg);
        objetoErroDto.setCode(status.value() + " ==> " + status.getReasonPhrase());

        ex.printStackTrace();
        try {
            emailService.enviarEmailHtml("Erro na loja virtual", ExceptionUtils.getStackTrace(ex), "diegodsoliveira@gmail.com");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

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

        ex.printStackTrace();
        try {
            emailService.enviarEmailHtml("Erro na loja virtual", ExceptionUtils.getStackTrace(ex), "diegodsoliveira@gmail.com");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(objetoErroDto, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
