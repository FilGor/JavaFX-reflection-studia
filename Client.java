package sample;

import java.math.BigDecimal;

public class Client {

    private String name = "Client Name";
    private String surName = " Client Surname";
    private BigDecimal saldo = BigDecimal.valueOf(0.000);



    public Client() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
