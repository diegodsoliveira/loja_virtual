package com.diego.lojavirtual.dtos;


import java.io.Serializable;

public class ObjetoErroDto implements Serializable {

    private static final long serialVersionUID = 8201892365486108920L;
    private String msgErro;
    private String code;

    public String getMsgErro() {
        return msgErro;
    }

    public void setMsgErro(String msgErro) {
        this.msgErro = msgErro;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
