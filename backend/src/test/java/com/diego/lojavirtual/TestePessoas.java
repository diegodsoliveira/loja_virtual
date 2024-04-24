package com.diego.lojavirtual;

import com.diego.lojavirtual.controller.PessoaController;
import com.diego.lojavirtual.model.PessoaJuridica;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;

@SpringBootTest(classes = LojaVirtualApplication.class)
public class TestePessoas extends TestCase {

    @Autowired
    public PessoaController pessoaController;

    @Test
    public void testeCadPessoaJuridica() throws CustomException {

        PessoaJuridica pessoaJuridica = new PessoaJuridica();

        pessoaJuridica.setCnpj("" + Calendar.getInstance().getTimeInMillis());
        pessoaJuridica.setTipoPessoa("Jurídica");
        pessoaJuridica.setInscricaoEstadual("1238128973980");
        pessoaJuridica.setNomeFantasia("Teste Corporation");
        pessoaJuridica.setInscricaoMunicipall("901298073");
        pessoaJuridica.setNome("Diego Oliveira");
        pessoaJuridica.setEmail("diegodsoliveira@gmail.com");
        pessoaJuridica.setTelefone("8977981239");
        pessoaJuridica.setRazaoSocial("Teste Corporation");

        pessoaController.salvarPessoaJuridica(pessoaJuridica);

    }

}
