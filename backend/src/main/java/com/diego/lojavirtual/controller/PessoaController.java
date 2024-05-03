package com.diego.lojavirtual.controller;

import com.diego.lojavirtual.CustomException;
import com.diego.lojavirtual.enums.TipoPessoa;
import com.diego.lojavirtual.model.PessoaFisica;
import com.diego.lojavirtual.model.PessoaJuridica;
import com.diego.lojavirtual.model.dto.CepDTO;
import com.diego.lojavirtual.model.dto.cnpjws.ConsultaCnpjDTO;
import com.diego.lojavirtual.repository.PessoaFisicaRepository;
import com.diego.lojavirtual.repository.PessoaJuridicaRepository;
import com.diego.lojavirtual.service.PessoaService;
import com.diego.lojavirtual.util.ValidaCnpj;
import com.diego.lojavirtual.util.ValidaCpf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Controller
@RestController
@Transactional
public class PessoaController {

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired private PessoaService pessoaService;

    @ResponseBody
    @GetMapping(value = "**/consultaCep/{cep}")
    public ResponseEntity<CepDTO> consultaCep(@PathVariable("cep") String cep) {

        return new ResponseEntity<CepDTO>(pessoaService.consultaCep(cep), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/consultaCnpjReceitaWs/{cnpj}")
    public ResponseEntity<ConsultaCnpjDTO> consultaCnpjReceitaWs(@PathVariable("cnpj") String cnpj) {

        return new ResponseEntity<ConsultaCnpjDTO>(pessoaService.consultaCnpjReceitaWs(cnpj), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/consultaPfNome/{nome}")
    public ResponseEntity<List<PessoaFisica>> consultaPfNome(@PathVariable("nome") String nome) {

        return new ResponseEntity<>(pessoaFisicaRepository.pesquisaPorNomePf(nome.trim().toUpperCase()),HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/consultaPfCpf/{cpf}")
    public ResponseEntity<List<PessoaFisica>> consultaPfCpf(@PathVariable("cpf") String cpf) {

        return new ResponseEntity<>(pessoaFisicaRepository.existeCpfCadastrado(cpf),HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/consultaPjNome/{nome}")
    public ResponseEntity<List<PessoaJuridica>> consultaPjNome(@PathVariable("nome") String nome) {

        return new ResponseEntity<>(pessoaJuridicaRepository.pesquisaPorNomePj(nome.trim().toUpperCase()),HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/consultaPjCnpj/{cnpj}")
    public ResponseEntity<List<PessoaJuridica>> consultaPjCnpj(@PathVariable("cnpj") String cnpj) {

        return new ResponseEntity<>(pessoaJuridicaRepository.existeCnpjCadastrado(cnpj),HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(value = "**/salvarPessoaJuridica")
    public ResponseEntity<PessoaJuridica> salvarPessoaJuridica(@RequestBody @Valid PessoaJuridica pessoaJuridica) throws CustomException {

        if (pessoaJuridica == null) {
            throw new CustomException("Pessoa jurídica não pode ser NULL");
        } else {
            pessoaJuridica.setCnpj(ValidaCnpj.removeCaracteresCnpj(pessoaJuridica.getCnpj()));
        }

        if (pessoaJuridica.getTipoPessoa() == null) {
            throw new CustomException("Informe o tipo da pessoa que está sendo cadastrada (ex. JURIDICA)");
        }

        if (pessoaJuridica.getId() == null && pessoaJuridicaRepository.existeCnpjCadastrado(pessoaJuridica.getCnpj()) != null) {
            throw new CustomException("Já existe CNPJ cadastrado com o número: " + pessoaJuridica.getCnpj());
        }

        if (pessoaJuridica.getId() == null && pessoaJuridicaRepository.existeInscricaoEstadual(pessoaJuridica.getInscricaoEstadual()) != null) {
            throw new CustomException("Já existe inscrição estadual cadastrada com o número: " + pessoaJuridica.getInscricaoEstadual());
        }

        if (pessoaJuridica.getId() == null && pessoaJuridicaRepository.existeEmailCadastrado(pessoaJuridica.getEmail()) != null) {
            throw new CustomException("O email informado já está em uso na nossa base de dados: " + pessoaJuridica.getEmail());
        }

        if (!ValidaCnpj.isCNPJ(pessoaJuridica.getCnpj())) {
            throw new CustomException("Este número de CNPJ é inválido: " + pessoaJuridica.getCnpj());
        }

        if (pessoaJuridica.getId() == null || pessoaJuridica.getId() <= 0) {
            pessoaService.cadastraEndereco(pessoaJuridica);
        } else {
            pessoaService.atualizaEndereco(pessoaJuridica);

        }

        return  new ResponseEntity<>(pessoaService.salvarPessoaJuridica(pessoaJuridica), HttpStatus.OK);

    }

    @ResponseBody
    @PostMapping(value = "**/salvarPessoaFisica")
    public ResponseEntity<PessoaFisica> salvarPessoaFisica(@RequestBody @Valid PessoaFisica pessoaFisica) throws CustomException {

        if (pessoaFisica == null) {
            throw new CustomException("Pessoa física não pode ser NULL");
        } else {
            pessoaFisica.setCpf(ValidaCpf.removeCaracteresCpf(pessoaFisica.getCpf()));
        }

        if (pessoaFisica.getTipoPessoa() == null) {
            pessoaFisica.setTipoPessoa(TipoPessoa.FISICA.getDescricao());
        }

        if (pessoaFisica.getId() == null && pessoaFisicaRepository.existeEmailCadastrado(pessoaFisica.getEmail()) != null) {
            throw new CustomException("O email informado já está em uso na nossa base de dados: " + pessoaFisica.getEmail());
        }

        if (pessoaFisica.getId() == null && pessoaFisicaRepository.existeCpfCadastrado(pessoaFisica.getCpf()).size() > 0) {
            throw new CustomException("Já existe CPF cadastrado com o número: " + pessoaFisica.getCpf());
        }

        if (!ValidaCpf.isCPF(pessoaFisica.getCpf())) {
            throw new CustomException("Este número de CPF é inválido: " + pessoaFisica.getCpf());
        }

        if (pessoaFisica.getId() == null || pessoaFisica.getId() <= 0) {
            pessoaService.cadastraEndereco(pessoaFisica);
        } else {
            pessoaService.atualizaEndereco(pessoaFisica);

        }

        return  new ResponseEntity<>(pessoaService.salvarPessoaFisica(pessoaFisica), HttpStatus.OK);

    }
}
