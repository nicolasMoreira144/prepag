package com.wex.prepag.dtos;

public class EmissaoDto {
	
	private String nome;
	private Double saldo;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Double getSaldo() {
		return saldo;
	}
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	@Override
	public String toString() {
		return "EmissaoDto [nome=" + nome + ", saldo=" + saldo + "]";
	}
	
}
