package com.wex.prepag.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wex.prepag.model.Cartao;

@Repository
@Transactional
public class CartaoDaoImpl extends AbstractDao<Cartao, Long> implements CartaoDao {
	
}
