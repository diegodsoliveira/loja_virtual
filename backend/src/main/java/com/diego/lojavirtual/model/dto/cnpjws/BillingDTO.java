package com.diego.lojavirtual.model.dto.cnpjws;

import java.io.Serializable;

public class BillingDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean free;

    public boolean isDatabase() {
        return database;
    }

    public void setDatabase(boolean database) {
        this.database = database;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    private boolean database;

}
