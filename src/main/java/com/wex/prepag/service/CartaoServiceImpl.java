package com.wex.prepag.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wex.prepag.dao.CartaoDao;
import com.wex.prepag.model.Cartao;

@Service @Transactional
public class CartaoServiceImpl implements CartaoService {


	private static final Logger log = LoggerFactory.getLogger(CartaoServiceImpl.class);
	
	@Autowired
	private CartaoDao dao;
	
	@Override
	public void salvar(Cartao cartao) {
		dao.save(cartao);		
	}

	@Override
	public void editar(Cartao cartao) {
		dao.update(cartao);		
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);		
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Cartao> buscarPorId(Long id) {
		log.info("Buscando cartao por o Id {}", id);
		return Optional.ofNullable(dao.findById(id));
	}
	
	@Transactional(readOnly = true)
	@Override 
	public List<Cartao> buscarTodos() {
		
		return dao.findAll();
	}
	
}
