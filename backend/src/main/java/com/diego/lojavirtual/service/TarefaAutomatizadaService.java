package com.diego.lojavirtual.service;

import com.diego.lojavirtual.model.Usuario;
import com.diego.lojavirtual.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class TarefaAutomatizadaService {

    @Autowired private UsuarioRepository usuarioRepository;

    @Autowired private EmailService emailService;

    //@Scheduled(initialDelay = 2000, fixedDelay = 86400000)
    @Scheduled(cron = "0 0 11 * * *", zone = "America/Sao_Paulo")
    public void notificaUsuarioSenhaVencida() throws MessagingException, UnsupportedEncodingException, InterruptedException {

        List<Usuario> usuarios = usuarioRepository.verificaUsuarioSenhaVencida();

        for (Usuario usuario : usuarios) {

            StringBuilder msg = new StringBuilder();
            msg.append("Olá ").append(usuario.getPessoa().getNome()).append("<br>");
            msg.append("Aqui é o Diego da loja virtual XYZ<br><br>");
            msg.append("Sua senha está vencida e precisa ser alterada a cada 90 dias").append("<br>");

            emailService.enviarEmailHtml("Senha vencida", msg.toString(), usuario.getLogin());

            Thread.sleep(3000);
        }
    }
}
