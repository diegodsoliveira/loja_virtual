package com.diego.lojavirtual.service;

import com.diego.lojavirtual.model.PessoaJuridica;
import com.diego.lojavirtual.model.Usuario;
import com.diego.lojavirtual.repository.PessoaJuridicaRepository;
import com.diego.lojavirtual.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class PessoaService {

    @Autowired private JdbcTemplate jdbcTemplate;

    @Autowired private UsuarioRepository usuarioRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    public PessoaJuridica save(PessoaJuridica pessoaJuridica) {
        pessoaJuridica = pessoaJuridicaRepository.save(pessoaJuridica);

        Usuario usuarioPj = usuarioRepository.findUserByPessoa(pessoaJuridica.getId(), pessoaJuridica.getEmail());

        if (usuarioPj == null) {
            String constraint = usuarioRepository.consultaConstraintRole();
            if (constraint != null) {
                jdbcTemplate.execute("begin; alter table usuario_acesso drop CONSTRAINT " + constraint + "; commit;");
            }

            usuarioPj = new Usuario();
            usuarioPj.setDataAtualSenha(Calendar.getInstance().getTime());
            usuarioPj.setPessoa(pessoaJuridica);
            usuarioPj.setEmpresa(pessoaJuridica);
            usuarioPj.setLogin(pessoaJuridica.getEmail());

            String senha = "" + Calendar.getInstance().getTimeInMillis();
            String senhaCript = new BCryptPasswordEncoder().encode(senha);

            usuarioPj.setSenha(senhaCript);
            usuarioPj = usuarioRepository.save(usuarioPj);

            usuarioRepository.insereAcessoUsuarioPj(usuarioPj.getId());
        }

        return pessoaJuridica;
    }

}
