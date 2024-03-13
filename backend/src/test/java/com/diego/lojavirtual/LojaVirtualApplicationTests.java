package com.diego.lojavirtual;

import com.diego.lojavirtual.controller.AcessoController;
import com.diego.lojavirtual.model.Acesso;
import com.diego.lojavirtual.service.AcessoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
class LojaVirtualApplicationTests {

	@Autowired
	private AcessoController acessoController;

	@Test
	public void testaCadastroAcesso() {
		Acesso acesso = new Acesso();

		acesso.setDescricao("ROLE_ADMIN");
		acessoController.salvarAcesso(acesso);

		acesso.setDescricao("ROLE_SAC");
		acessoController.salvarAcesso(acesso);
	}
	@Test
	void contextLoads() {
	}

}
