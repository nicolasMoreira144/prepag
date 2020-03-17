package com.wex.prepag.service;

import java.util.List;
import java.util.Optional;

import com.wex.prepag.model.Venda;

public interface VendaService {
	
	void salvar(Venda venda);
	
	void editar(Venda venda);
	
	void excluir(Long id);
	
	Optional<Venda> buscarPorId(Long id);
	
	List<Venda> buscarTodos();

}
