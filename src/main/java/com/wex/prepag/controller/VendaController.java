package com.wex.prepag.controller;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wex.prepag.dtos.StatusRetornoDto;
import com.wex.prepag.dtos.VendaDto;
import com.wex.prepag.model.Cartao;
import com.wex.prepag.model.Venda;
import com.wex.prepag.response.Response;
import com.wex.prepag.role.GenerateCard;
import com.wex.prepag.service.CartaoService;
import com.wex.prepag.service.VendaService;

@Controller
@CrossOrigin
@RequestMapping("/vendas")
public class VendaController {

	private static final Logger log = LoggerFactory.getLogger(CartaoController.class);

	@Autowired
	private VendaService vendaService;

	@Autowired
	private CartaoService cartaoService;

	@PostMapping("/comprar")
	public ResponseEntity<Response<StatusRetornoDto>> cadastrar(@Valid @RequestBody VendaDto vendaDto, BindingResult result)
			throws NoSuchAlgorithmException {
		log.info("Realizando compra: {}", vendaDto.toString());

		Response<StatusRetornoDto> response = new Response<StatusRetornoDto>();
		createChaveVenda(vendaDto);
		Optional<Cartao> cartaoDaCompra = cartaoService.buscarPorId(vendaDto.getNumero());
		
		Cartao cartao = new Cartao();

		if (cartaoDaCompra.isPresent()) {
			
			cartao = cartaoDaCompra.get();

			if (cartao.getValidade().isBefore(LocalDate.now()))
				result.addError(new ObjectError("Cartao", "Cartão Vencido."));

			if (vendaDto.getValor() > cartao.getSaldo())
				result.addError(new ObjectError("Cartao", "Saldo do cartão não suficiente."));

			if (!verificaSenha(vendaDto.getSenha(), cartao))
				result.addError(new ObjectError("Cartao", "Senha do cartão está errada."));
			
			if (!vendaDto.getCvv().equals(cartao.getCvv()))
				result.addError(new ObjectError("Cartao", "CVV do cartão está errado."));
		} else {
			result.addError(new ObjectError("Cartao", "Cartão não cadastrado."));

		}

		if (result.hasErrors()) {
			log.error("Erro validação compra: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		Venda venda = this.converterDtoParaVenda(vendaDto, cartaoDaCompra);
		cartao.comprar(vendaDto.getValor());
		
		this.vendaService.salvar(venda);
		this.cartaoService.editar(cartao);
		
		StatusRetornoDto statusReq = new StatusRetornoDto();
		
		statusReq.setCodigo("00");
		statusReq.setSaldo(cartao.getSaldo());
		
		response.setData(statusReq);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Response<VendaDto>> buscar(@PathVariable("id") Long id) {
		log.info("Buscando venda por: {}", id);
		Response<VendaDto> response = new Response<VendaDto>();
		Optional<Venda> venda = this.vendaService.buscarPorId(id);
		
		
		if (!venda.isPresent()) {
			log.info("Erro ao buscar venda ID: {} é inválido.", id);
			response.getErrors().add("Erro ao buscar venda. venda não encontrada com o id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		Venda vendaPesquisada = venda.get();
		VendaDto vendaDto = populaDto(vendaPesquisada);
		
		response.setData(vendaDto);
		return ResponseEntity.ok(response);
	}

	
	@DeleteMapping("/apagar/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo venda: {}", id);
		Response<String> response = new Response<String>();
		Optional<Venda> venda = this.vendaService.buscarPorId(id);

		if (!venda.isPresent()) {
			log.info("Erro ao remover venda ID: {} é inválido.", id);
			response.getErrors().add("Erro ao pesquisar venda. venda não encontrada para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.vendaService.excluir(id);
		log.info("Venda removidaS: {}", id);
		return ResponseEntity.ok(new Response<String>());
	}

	
	
	private void createChaveVenda(VendaDto vendaDto) {
		Random generator = new Random();
		int x = 10000 + generator.nextInt(90000);
		long id = x;
		vendaDto.setId(id);
		
	}
	private boolean verificaSenha(String senha, Cartao cartao) {

		boolean result = false;
		String senhaCri = GenerateCard.criptografia(senha);

		if (senhaCri.equals(cartao.getSenha()))
			result = true;

		return result;
	}

	private Venda converterDtoParaVenda(VendaDto vendaDto, Optional<Cartao> cartao) {
		Venda venda = new Venda();
		venda.setId(vendaDto.getId());
		venda.setCartao(cartao.get());
		venda.setCvv(vendaDto.getCvv());
		venda.setNomeEstabelecimento(vendaDto.getNomeEstabelecimento());
		venda.setSenha(vendaDto.getSenha());
		venda.setValor(vendaDto.getValor());
		venda.setNumero(vendaDto.getNumero());
		venda.setValidade(vendaDto.getValidade());
		return venda;
	}

	private VendaDto populaDto(Venda venda) {
		VendaDto vendaDto = new VendaDto();
		vendaDto.setId(venda.getId());
		vendaDto.setCvv(venda.getCvv());
		vendaDto.setNomeEstabelecimento(venda.getNomeEstabelecimento());
		vendaDto.setSenha(venda.getSenha());
		vendaDto.setValor(venda.getValor());
		vendaDto.setNumero(venda.getNumero());
		vendaDto.setValidade(venda.getValidade());
		return vendaDto;
	}

}
