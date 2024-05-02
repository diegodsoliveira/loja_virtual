package com.diego.lojavirtual.controller;

import com.diego.lojavirtual.CustomException;
import com.diego.lojavirtual.model.Endereco;
import com.diego.lojavirtual.model.PessoaFisica;
import com.diego.lojavirtual.model.PessoaJuridica;
import com.diego.lojavirtual.model.dto.CepDTO;
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
    @PostMapping(value = "**/salvarPessoaJuridica")
    public ResponseEntity<PessoaJuridica> salvarPessoaJuridica(@RequestBody @Valid PessoaJuridica pessoaJuridica) throws CustomException {

        if (pessoaJuridica == null) {
            throw new CustomException("Pessoa jurídica não pode ser NULL");
        } else {
            pessoaJuridica.setCnpj(ValidaCnpj.removeCaracteresCnpj(pessoaJuridica.getCnpj()));
        }

        if (pessoaJuridica.getId() == null && pessoaJuridicaRepository.existeCnpjCadastrado(pessoaJuridica.getCnpj()) != null) {
            throw new CustomException("Já existe CNPJ cadastrado com o número: " + pessoaJuridica.getCnpj());
        }

        if (pessoaJuridica.getId() == null && pessoaJuridicaRepository.existeInscricaoEstadual(pessoaJuridica.getInscricaoEstadual()) != null) {
            throw new CustomException("Já existe inscrição estadual cadastrada com o número: " + pessoaJuridica.getInscricaoEstadual());
        }

        if (!ValidaCnpj.isCNPJ(pessoaJuridica.getCnpj())) {
            throw new CustomException("Este número de CNPJ é inválido: " + pessoaJuridica.getCnpj());
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

        if (pessoaFisica.getId() == null && pessoaFisicaRepository.existeCpfCadastrado(pessoaFisica.getCpf()).size() > 0) {

            throw new CustomException("Já existe CPF cadastrado com o número: " + pessoaFisica.getCpf());
        }

        if (!ValidaCpf.isCPF(pessoaFisica.getCpf())) {
            throw new CustomException("Este número de CPF é inválido: " + pessoaFisica.getCpf());
        }

        if (pessoaFisica.getId() == null || pessoaFisica.getId() <= 0) {
            for (int i = 0; i < pessoaFisica.getEnderecos().size(); i++) {
                CepDTO cepDTO = pessoaService.consultaCep(pessoaFisica.getEnderecos().get(i).getCep());

                pessoaFisica.getEnderecos().get(i).setBairro(cepDTO.getBairro());
                pessoaFisica.getEnderecos().get(i).setCidade(cepDTO.getLocalidade());
                pessoaFisica.getEnderecos().get(i).setComplemento(cepDTO.getComplemento());
                pessoaFisica.getEnderecos().get(i).setRuaLogradouro(cepDTO.getLogradouro());
                pessoaFisica.getEnderecos().get(i).setUf(cepDTO.getUf());
                pessoaFisica.getEnderecos().get(i).setNumero(cepDTO.getComplemento());
            }
        }

        return  new ResponseEntity<>(pessoaService.salvarPessoaFisica(pessoaFisica), HttpStatus.OK);

    }
}
