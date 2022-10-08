package com.diego.lojavirtual;

import com.diego.lojavirtual.model.Acesso;
import com.diego.lojavirtual.service.AcessoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
class LojaVirtualApplicationTests {

	@Autowired
	private AcessoService acessoService;

	@Test
	public void testaCadastroAcesso() {
		Acesso acesso = new Acesso();

		acesso.setDescricao("ROLE_ADMIN");
		acessoService.create(acesso);
	}
	@Test
	void contextLoads() {
	}

}
