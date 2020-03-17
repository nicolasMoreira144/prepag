package com.wex.prepag.dao;

import java.util.List;

import com.wex.prepag.model.Cartao;

public interface CartaoDao {

	void save(Cartao cartao);

    void update(Cartao cartao);

    void delete(Long id);

    Cartao findById(Long id);

    List<Cartao> findAll();


}
