package com.wex.prepag.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wex.prepag.dtos.CartaoDto;
import com.wex.prepag.dtos.EmissaoDto;
import com.wex.prepag.model.Cartao;
import com.wex.prepag.response.Response;
import com.wex.prepag.role.GenerateCard;
import com.wex.prepag.service.CartaoService;

@Controller
@CrossOrigin
@RequestMapping("/cartoes")
public class CartaoController {
	
	private static final Logger log = LoggerFactory.getLogger(CartaoController.class);
	
	@Autowired
	private CartaoService cartaoService;
	
	@PostMapping("/emitir")
	public ResponseEntity<Response<CartaoDto>> cadastrar(@Valid @RequestBody EmissaoDto emissaoDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Criando cartão: {}", emissaoDto.toString());
		
		GenerateCard newCard = new GenerateCard();
		newCard.generateCard();
		
		CartaoDto cartaoDto = populaDto(emissaoDto, newCard);
		Response<CartaoDto> response = new Response<CartaoDto>();
 
		
		Cartao cartao = this.converterDtoParaCartao(cartaoDto, result);

		if (result.hasErrors()) {
			log.error("Erro validação cartão: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.cartaoService.salvar(cartao);
		// exibir senha apenas no retorno
		cartaoDto.setSenha(newCard.getPassword());
		
		response.setData(cartaoDto);
		return ResponseEntity.ok(response);
	}	
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Response<CartaoDto>> buscar(@PathVariable("id") Long id) {
		log.info("Buscando cartão por: {}", id);
		Response<CartaoDto> response = new Response<CartaoDto>();
		Optional<Cartao> cartao = this.cartaoService.buscarPorId(id);
		
		
		if (!cartao.isPresent()) {
			log.info("Erro ao buscar devido cartão ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover cartão. cartão não encontrado com o id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		Cartao cartaoPesquisado = cartao.get();
		CartaoDto cartaoDto = populaDto(cartaoPesquisado);
		
		response.setData(cartaoDto);
		return ResponseEntity.ok(response);
	}

	
	@DeleteMapping("/apagar/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		log.info("Removendo cartao: {}", id);
		Response<String> response = new Response<String>();
		Optional<Cartao> cartao = this.cartaoService.buscarPorId(id);

		if (!cartao.isPresent()) {
			log.info("Erro ao remover devido cartão ID: {} ser inválido.", id);
			response.getErrors().add("Erro ao remover cartão. cartão não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.cartaoService.excluir(id);
		log.info("Cartao removido: {}", id);
		return ResponseEntity.ok(new Response<String>());
	}

	
	
	private CartaoDto populaDto(EmissaoDto emissaoDto, GenerateCard newCard) {
		
		CartaoDto cartaoDto = new CartaoDto();
		cartaoDto.setId(newCard.getNumberCard());
		cartaoDto.setNome(emissaoDto.getNome());
		cartaoDto.setCvv(newCard.getCvv());
		cartaoDto.setValidade(newCard.getValidade());
		cartaoDto.setSenha(newCard.getPasswordCrip());
		cartaoDto.setSaldo(emissaoDto.getSaldo());
		
		return cartaoDto;
	}
	
	private CartaoDto populaDto(Cartao cartao) {
		
		CartaoDto cartaoDto = new CartaoDto();
		cartaoDto.setId(cartao.getId());
		cartaoDto.setNome(cartao.getNome());
		cartaoDto.setCvv(cartao.getCvv());
		cartaoDto.setValidade(cartao.getValidade());
		cartaoDto.setSenha(cartao.getSenha());
		cartaoDto.setSaldo(cartao.getSaldo());
		
		return cartaoDto;
	}
	
	private Cartao converterDtoParaCartao(CartaoDto cartaoDto, BindingResult result)  {
		Cartao cartao = new Cartao();
		cartao.setId(cartaoDto.getId());
		cartao.setNome(cartaoDto.getNome());
		cartao.setCvv(cartaoDto.getCvv());
		cartao.setValidade(cartaoDto.getValidade());
		cartao.setSenha(cartaoDto.getSenha());
		cartao.setSaldo(cartaoDto.getSaldo());
		return cartao;
	}

}

