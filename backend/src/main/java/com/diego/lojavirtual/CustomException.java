package com.diego.lojavirtual;

import java.io.Serializable;

public class CustomException extends Exception implements Serializable {

    private static final long serialVersionUID = -8506817468282858441L;

    public CustomException(String msg) {
        super(msg);
    }
}
