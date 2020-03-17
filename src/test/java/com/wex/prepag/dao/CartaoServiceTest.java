package com.wex.prepag.dao;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.wex.prepag.model.Cartao;
import com.wex.prepag.service.CartaoServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CartaoServiceTest {
	
	@Autowired
	private CartaoServiceImpl cartaoService;
	
	private static final Long ID = 514636450001009L;
	private static final String NOME = "Nicolas Souza";
	private static final String CVV = "456";
	private static final LocalDate VALIDADE = LocalDate.now().plusYears(2);
	private static final String SENHA = "456";
	private static final Double SALDO = 1500.00;
	
	@Before
	public void setUp() throws Exception {
		Cartao cartao = new Cartao();
		cartao.setId(ID);
		cartao.setNome(NOME);
		cartao.setCvv(CVV);
		cartao.setValidade(VALIDADE);
		cartao.setSenha(SENHA);
		cartao.setSaldo(SALDO);
		this.cartaoService.salvar(cartao);
	}
	
	@After
    public final void tearDown() { 
		this.cartaoService.excluir(ID);;
	}

	@Test
	public void testBuscarPorCnpj() {
		Optional<Cartao> cartaoOptional = this.cartaoService.buscarPorId(ID);
		if(cartaoOptional.isPresent()) {
			Cartao cartao = cartaoOptional.get();
			assertEquals(ID, cartao.getId());
			
		}else {
			return;
		}
	}


}
