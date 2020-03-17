package com.wex.prepag.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wex.prepag.dao.VendaDao;
import com.wex.prepag.model.Venda;

@Service @Transactional(readOnly = false)
public class VendaServiceImpl implements VendaService{
	private static final Logger log = LoggerFactory.getLogger(VendaServiceImpl.class);
	
	@Autowired
	private VendaDao dao;
	
	@Override
	public void salvar(Venda venda) {
		dao.save(venda);		
	}

	@Override
	public void editar(Venda venda) {
		dao.update(venda);		
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);		
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Venda> buscarPorId(Long id) {
		log.info("Buscando venda por o Id {}", id);
		return Optional.ofNullable(dao.findById(id));
	}
	
	@Transactional(readOnly = true)
	@Override 
	public List<Venda> buscarTodos() {
		
		return dao.findAll();
	}
	
}
