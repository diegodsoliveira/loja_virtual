package com.diego.lojavirtual.controller;

import com.diego.lojavirtual.CustomException;
import com.diego.lojavirtual.model.PessoaJuridica;
import com.diego.lojavirtual.repository.PessoaJuridicaRepository;
import com.diego.lojavirtual.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@Controller
@RestController
@Transactional
public class PessoaController {

    @Autowired
    public PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired public PessoaService pessoaService;

    @ResponseBody
    @PostMapping(value = "**/salvarPessoaJuridica")
    public ResponseEntity<PessoaJuridica> salvarPessoaJuridica(@RequestBody PessoaJuridica pessoaJuridica) throws CustomException {

        if (pessoaJuridica == null) {
            throw new CustomException("Pessoa jurídica não pode ser NULL");
        }

        if (pessoaJuridica.getId() == null && pessoaJuridicaRepository.existeCnpjCadastrado(pessoaJuridica.getCnpj()) != null) {
            throw new CustomException("Já existe CNPJ cadastrado com o número: " + pessoaJuridica.getCnpj());
        }

        return  new ResponseEntity<>(pessoaService.save(pessoaJuridica), HttpStatus.OK);

    }
}
