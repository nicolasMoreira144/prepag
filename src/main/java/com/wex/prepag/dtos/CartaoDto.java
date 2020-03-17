package com.wex.prepag.dtos;

import java.time.LocalDate;

public class CartaoDto {
	
	private Long id;
	private String nome;
	private String cvv;
	private LocalDate validade;
	private String senha;
	private Double saldo;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	public LocalDate getValidade() {
		return validade;
	}
	public void setValidade(LocalDate validade) {
		this.validade = validade;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Double getSaldo() {
		return saldo;
	}
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "CartaoDto [id=" + id + ", nome=" + nome + ", cvv=" + cvv + ", validade=" + validade + ", senha=" + senha
				+ ", saldo=" + saldo + "]";
	}
	
	
}
