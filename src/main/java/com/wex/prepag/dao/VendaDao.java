package com.wex.prepag.dao;

import java.util.List;

import com.wex.prepag.model.Venda;

public interface VendaDao {
	
	void save(Venda venda);

    void update(Venda venda);

    void delete(Long id);

    Venda findById(Long id);

    List<Venda> findAll();


}
