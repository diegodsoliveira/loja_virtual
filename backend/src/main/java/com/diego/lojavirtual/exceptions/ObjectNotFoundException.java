package com.diego.lojavirtual.exceptions;

import java.io.Serializable;

public class ObjectNotFoundException extends RuntimeException implements Serializable {

    public static final long serialVersionUID = 1L;

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
