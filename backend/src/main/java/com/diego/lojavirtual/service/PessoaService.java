package com.diego.lojavirtual.service;

import com.diego.lojavirtual.dtos.CepDTO;
import com.diego.lojavirtual.dtos.cnpjws.ConsultaCnpjDTO;
import com.diego.lojavirtual.enums.TipoAcesso;
import com.diego.lojavirtual.exceptions.ObjectNotFoundException;
import com.diego.lojavirtual.model.*;
import com.diego.lojavirtual.repository.EnderecoRepository;
import com.diego.lojavirtual.repository.PessoaFisicaRepository;
import com.diego.lojavirtual.repository.PessoaJuridicaRepository;
import com.diego.lojavirtual.repository.UsuarioRepository;
import com.diego.lojavirtual.util.ValidaCnpj;
import com.diego.lojavirtual.util.ValidaCpf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EnderecoRepository enderecoRepository;


    public PessoaJuridica salvarPessoaJuridica(PessoaJuridica pessoaJuridica) {

        for (int i = 0; i < pessoaJuridica.getEnderecos().size(); i++) {
            pessoaJuridica.getEnderecos().get(i).setPessoa(pessoaJuridica);
            pessoaJuridica.getEnderecos().get(i).setEmpresa(pessoaJuridica);
        }

        pessoaJuridica.setCnpj(ValidaCnpj.removeCaracteresCnpj(pessoaJuridica.getCnpj()));

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
            pessoaFisica.getEnderecos().get(i).setEmpresa(pessoaFisica.getEmpresa());
        }

        pessoaFisica.setCpf(ValidaCpf.removeCaracteresCpf(pessoaFisica.getCpf()));

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
            usuarioPf.setEmpresa(pessoaFisica.getEmpresa());
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

    public CepDTO consultaCep(String cep) {
        return new RestTemplate().getForEntity("https://viacep.com.br/ws/" + cep + "/json/", CepDTO.class).getBody();
    }

    public ConsultaCnpjDTO consultaCnpjReceitaWs(String cnpj) {
        return new RestTemplate().getForEntity("https://receitaws.com.br/v1/cnpj/" + cnpj, ConsultaCnpjDTO.class).getBody();
    }

    // verificar esta lógica depois

    public void cadastraEndereco(Pessoa pessoa) {

        for (int i = 0; i < pessoa.getEnderecos().size(); i++) {
            CepDTO cepDTO = consultaCep(pessoa.getEnderecos().get(i).getCep());

            pessoa.getEnderecos().get(i).setBairro(cepDTO.getBairro());
            pessoa.getEnderecos().get(i).setCidade(cepDTO.getLocalidade());
            pessoa.getEnderecos().get(i).setComplemento(cepDTO.getComplemento());
            pessoa.getEnderecos().get(i).setRuaLogradouro(cepDTO.getLogradouro());
            pessoa.getEnderecos().get(i).setUf(cepDTO.getUf());
            pessoa.getEnderecos().get(i).setNumero(cepDTO.getComplemento());
        }
    }

    public void atualizaEndereco(Pessoa pessoa) {
        for (int i = 0; i < pessoa.getEnderecos().size(); i++) {
            Endereco endereco = enderecoRepository.findById(pessoa.getEnderecos().get(i).getId()).get();

            if (!endereco.getCep().equals(pessoa.getEnderecos().get(i).getCep())) {
                CepDTO cepDTO = consultaCep(pessoa.getEnderecos().get(i).getCep());

                pessoa.getEnderecos().get(i).setBairro(cepDTO.getBairro());
                pessoa.getEnderecos().get(i).setCidade(cepDTO.getLocalidade());
                pessoa.getEnderecos().get(i).setComplemento(cepDTO.getComplemento());
                pessoa.getEnderecos().get(i).setRuaLogradouro(cepDTO.getLogradouro());
                pessoa.getEnderecos().get(i).setUf(cepDTO.getUf());
                pessoa.getEnderecos().get(i).setNumero(cepDTO.getComplemento());
            }
        }
    }

    public PessoaJuridica findPjById(Long id) {
        Optional<PessoaJuridica> obj = pessoaJuridicaRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("ID empresa não encontrado. Id: " + id));
    }

}
