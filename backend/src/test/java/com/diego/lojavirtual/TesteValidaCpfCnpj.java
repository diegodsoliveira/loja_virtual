package com.diego.lojavirtual;

import com.diego.lojavirtual.util.ValidaCnpj;
import com.diego.lojavirtual.util.ValidaCpf;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = LojaVirtualApplication.class)
public class TesteValidaCpfCnpj {

    @Test
    public static void main(String[] args) {

        boolean isCNPJ = ValidaCnpj.isCNPJ("38.557.576/0001-27");

        System.out.println("CNPJ é válido: " + isCNPJ);

        boolean isCPF = ValidaCpf.isCPF("977.128.220-42");

        System.out.println("CPF é válido: " + isCPF);

    }
}
