package com.diego.lojavirtual.service;

import com.diego.lojavirtual.model.Acesso;
import com.diego.lojavirtual.repository.AcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcessoService {

    @Autowired
    private AcessoRepository acessoRepository;

    public Acesso save(Acesso obj) {
        obj.setId(null);
        return acessoRepository.save(obj);
    }
}
