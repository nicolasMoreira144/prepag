package com.wex.prepag.dtos;

public class StatusRetornoDto {
	
	private String codigo;
	private Double saldo;
	
	public Double getSaldo() {
		return saldo;
	}
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@Override
	public String toString() {
		return "StatusRetornoDto [codigo=" + codigo + ", saldo=" + saldo + "]";
	}
	
}
