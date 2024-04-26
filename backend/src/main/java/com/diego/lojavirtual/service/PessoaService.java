package com.diego.lojavirtual.service;

import com.diego.lojavirtual.enums.TipoAcesso;
import com.diego.lojavirtual.model.PessoaFisica;
import com.diego.lojavirtual.model.PessoaJuridica;
import com.diego.lojavirtual.model.Usuario;
import com.diego.lojavirtual.repository.PessoaFisicaRepository;
import com.diego.lojavirtual.repository.PessoaJuridicaRepository;
import com.diego.lojavirtual.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

@Service
public class PessoaService {

    @Autowired private JdbcTemplate jdbcTemplate;

    @Autowired private UsuarioRepository usuarioRepository;

    @Autowired private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired private EmailService emailService;


    public PessoaJuridica salvarPessoaJuridica(PessoaJuridica pessoaJuridica) {

        for (int i = 0; i < pessoaJuridica.getEnderecos().size(); i++) {
            pessoaJuridica.getEnderecos().get(i).setPessoa(pessoaJuridica);
            pessoaJuridica.getEnderecos().get(i).setEmpresa(pessoaJuridica);
        }

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

            usuarioRepository.insereAcessoUsuario(usuarioPj.getId(), TipoAcesso.USER.toString());
            usuarioRepository.insereAcessoUsuario(usuarioPj.getId(), TipoAcesso.ADMIN.toString());
        }

        return pessoaJuridica;
    }

    public PessoaFisica salvarPessoaFisica(PessoaFisica pessoaFisica) {
        for (int i = 0; i < pessoaFisica.getEnderecos().size(); i++) {
            pessoaFisica.getEnderecos().get(i).setPessoa(pessoaFisica);
        }

        pessoaFisica = pessoaFisicaRepository.save(pessoaFisica);

        Usuario usuarioPf = usuarioRepository.findUserByPessoa(pessoaFisica.getId(), pessoaFisica.getEmail());

        if (usuarioPf == null) {
            String constraint = usuarioRepository.consultaConstraintRole();
            if (constraint != null) {
                jdbcTemplate.execute("begin; alter table usuario_acesso drop CONSTRAINT " + constraint + "; commit;");
            }

            usuarioPf = new Usuario();
            usuarioPf.setDataAtualSenha(Calendar.getInstance().getTime());
            usuarioPf.setPessoa(pessoaFisica);
            usuarioPf.setEmpresa(pessoaFisica);
            usuarioPf.setLogin(pessoaFisica.getEmail());

            String senha = "" + Calendar.getInstance().getTimeInMillis();
            String senhaCript = new BCryptPasswordEncoder().encode(senha);

            usuarioPf.setSenha(senhaCript);
            usuarioPf = usuarioRepository.save(usuarioPf);

            usuarioRepository.insereAcessoUsuario(usuarioPf.getId(), TipoAcesso.USER.toString());

            StringBuilder mensagem = new StringBuilder();
            mensagem.append("<b>Seja bem vindo à loja virtual XYZ</b><br><br>");
            mensagem.append("dados para acesso ao site<br>");
            mensagem.append("login: ").append(usuarioPf.getLogin()).append("<br>");
            mensagem.append("senha: ").append(senha).append("<br><br>");
            mensagem.append("Este é um email teste.");

            try {
                emailService.enviarEmailHtml("Usuário criado na loja virtual", mensagem.toString(), pessoaFisica.getEmail());
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return pessoaFisica;
    }

}
