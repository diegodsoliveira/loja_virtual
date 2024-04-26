package com.diego.lojavirtual;

import com.diego.lojavirtual.controller.PessoaController;
import com.diego.lojavirtual.model.PessoaFisica;
import com.diego.lojavirtual.model.PessoaJuridica;
import com.diego.lojavirtual.repository.PessoaJuridicaRepository;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;

@SpringBootTest(classes = LojaVirtualApplication.class)
public class TestePessoas extends TestCase {

    @Autowired
    private PessoaController pessoaController;

    @Autowired private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Test
    public void testeCadPessoaJuridica() throws CustomException {

        PessoaJuridica pessoaJuridica = new PessoaJuridica();

        pessoaJuridica.setCnpj("" + Calendar.getInstance().getTimeInMillis());
        pessoaJuridica.setTipoPessoa("Jurídica");
        pessoaJuridica.setInscricaoEstadual("1238128973980");
        pessoaJuridica.setNomeFantasia("Teste Corporation");
        pessoaJuridica.setInscricaoMunicipall("901298073");
        pessoaJuridica.setNome("Diego Oliveira");
        pessoaJuridica.setEmail("testesalvarpj@gmail.com");
        pessoaJuridica.setTelefone("8977981239");
        pessoaJuridica.setRazaoSocial("Teste Corporation");

        pessoaController.salvarPessoaJuridica(pessoaJuridica);

    }

    @Test
    public void testeCadPessoaFisica() throws CustomException {

        PessoaFisica pessoaFisica = new PessoaFisica();

        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.existeCnpjCadastrado("1713971777615");

        pessoaFisica.setCpf("664.746.060-88");
        pessoaFisica.setTipoPessoa("Física");
        pessoaFisica.setNome("Usuário teste");
        pessoaFisica.setEmail("teste321@teste.com");
        pessoaFisica.setTelefone("8977981239");
        pessoaFisica.setEmpresa(pessoaJuridica);

        pessoaController.salvarPessoaFisica(pessoaFisica);

    }

}
