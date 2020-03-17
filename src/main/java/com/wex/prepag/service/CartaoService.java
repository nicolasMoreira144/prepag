package com.wex.prepag.service;

import java.util.List;
import java.util.Optional;

import com.wex.prepag.model.Cartao;

public interface CartaoService {
	
	void salvar(Cartao cartao);
	
	void editar(Cartao cartao);
	
	void excluir(Long id);
	
	Optional<Cartao> buscarPorId(Long id);
	
	List<Cartao> buscarTodos();

}
